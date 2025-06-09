package com.easypay.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.Employee;
import com.easypay.model.EmployeeBenefit;
import com.easypay.model.JobTitle;
import com.easypay.model.Leave;
import com.easypay.model.Payroll;
import com.easypay.model.PayrollPolicy;
import com.easypay.repository.EmployeeBenefitRepository;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.LeaveRepository;
import com.easypay.repository.PayrollPolicyRepository;
import com.easypay.repository.PayrollRepository;

@Service
public class PayrollService {
	
	private PayrollRepository payrollRepository;
	private EmployeeRepository employeeRepository;
	private LeaveRepository leaveRepository;
	private PayrollPolicyRepository payrollPolicyRepository;
	private EmployeeBenefitRepository employeeBenefitRepository;

	public PayrollService(PayrollRepository payrollRepository, EmployeeRepository employeeRepository,
			LeaveRepository leaveRepository, PayrollPolicyRepository payrollPolicyRepository,
			EmployeeBenefitRepository employeeBenefitRepository) {
		super();
		this.payrollRepository = payrollRepository;
		this.employeeRepository = employeeRepository;
		this.leaveRepository = leaveRepository;
		this.payrollPolicyRepository = payrollPolicyRepository;
		this.employeeBenefitRepository = employeeBenefitRepository;
	}

	public Payroll generatePayroll(int employeeId,Payroll payroll) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()->new RuntimeException("Invalid id given. Employee not found"));
		
		JobTitle jobTitle = employee.getJobTitle();

        // 1. Basic Inputs
        double basic = jobTitle.getBasicSalary();
        double hra = basic * (jobTitle.getHraRate() / 100.0);
        double da = basic * (jobTitle.getDaRate() / 100.0);
        double jobBasedAllowances = jobTitle.getOtherAllowances();

        // Fetch employee-specific benefits
        List<EmployeeBenefit> benefits = employeeBenefitRepository.findActiveBenefitsByEmployeeId(employeeId);

        double benefitAllowances = benefits.stream()
         .mapToDouble(b -> b.getBenefit().getAllowanceAmount())
         .sum();

        double otherAllowances = jobBasedAllowances + benefitAllowances;
        
        // 2. Total Earnings
        double totalEarnings = basic + hra + da + otherAllowances;

        // 3. Fetch Payroll Policy
        PayrollPolicy policy = payrollPolicyRepository.findActivePolicyForEmployee(employeeId)
                .orElseThrow(() -> new RuntimeException("No payroll policy found"));

        double taxDeduction = totalEarnings * (policy.getTaxRate() / 100.0);
        double pfContribution = basic * (policy.getPfRate() / 100.0);

        // 4. Compute unpaid leave deductions
        String payPeriod = payroll.getPayPeriod();
        LocalDate startDate = YearMonth.parse(payPeriod).atDay(1);
        LocalDate endDate = YearMonth.parse(payPeriod).atEndOfMonth();

        List<Leave> unpaidLeaves = leaveRepository.findApprovedUnpaidLeavesWithinPeriod(employeeId, startDate, endDate);

        int unpaidDays = unpaidLeaves.stream()
                .mapToInt(l -> (int) ChronoUnit.DAYS.between(l.getStartDate(), l.getEndDate()) + 1)
                .sum();

        int workingDays = endDate.lengthOfMonth();
        double perDaySalary = basic / workingDays;
        double unpaidLeaveDeduction = unpaidDays * perDaySalary;

        // 5. Total Deductions and Net Pay
        double totalDeductions = taxDeduction + pfContribution + unpaidLeaveDeduction;
        double netPay = totalEarnings - totalDeductions;
		
        //Save Payroll
        payroll.setEmployee(employee);
        payroll.setBasicSalary(basic);
        payroll.setDa(da);
        payroll.setHra(hra);
        payroll.setOtherAllowances(otherAllowances);
        payroll.setPfContribution(pfContribution);
        payroll.setTaxDeduction(taxDeduction);
        payroll.setUnpaidLeaveDeduction(unpaidLeaveDeduction);
        payroll.setTotalEarnings(totalEarnings);
        payroll.setTotalDeductions(totalDeductions);
        payroll.setNetSalary(netPay);
        
		return payrollRepository.save(payroll);
	}

	public List<Payroll> getAll() {
		return payrollRepository.findAll();
	}

	public Payroll getPayrollById(int payrollId) {
		return payrollRepository.findById(payrollId)
				.orElseThrow(()->new RuntimeException("Invalid Id given"));
	}

}

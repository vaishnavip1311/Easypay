package com.easypay.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.easypay.dto.HrDashboardDTO;
import com.easypay.model.Employee;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.PayrollPolicyRepository;
import com.easypay.repository.PayrollRepository;

@Service
public class HrDashboardService {

    private EmployeeRepository employeeRepo;
    private PayrollRepository payrollRepo;
    private PayrollPolicyRepository policyRepo;

    public HrDashboardService(EmployeeRepository employeeRepo, PayrollRepository payrollRepo, PayrollPolicyRepository policyRepo) {
        this.employeeRepo = employeeRepo;
        this.payrollRepo = payrollRepo;
        this.policyRepo = policyRepo;
    }

    public HrDashboardDTO getDashboardStats() {
        HrDashboardDTO dto = new HrDashboardDTO();

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        dto.setTotalEmployees((int) employeeRepo.count());
        dto.setActivePolicies((int) policyRepo.count());
        dto.setMonthlyPayrollProcessed(payrollRepo.getTotalPayrollThisMonth(currentMonth, currentYear));

        // Count employees by employment type
        List<Employee> employees = employeeRepo.findAll();
        Map<String, Integer> dist = new HashMap<>();
        for (Employee e : employees) {
            String type = e.getEmployementType(); // Ensure correct spelling
            dist.put(type, dist.getOrDefault(type, 0) + 1);
        }
        dto.setEmployeeTypeDistribution(dist);

        return dto;
    }
}
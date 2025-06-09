package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.easypay.model.Payroll;
import com.easypay.model.Payslip;
import com.easypay.repository.PayrollRepository;
import com.easypay.repository.PayslipRepository;

@Service
public class PayslipService {
	
	private PayslipRepository payslipRepository;
	private PayrollRepository payrollRepository;

	public PayslipService(PayslipRepository payslipRepository, PayrollRepository payrollRepository) {
		super();
		this.payslipRepository = payslipRepository;
		this.payrollRepository = payrollRepository;
	}

	public Payslip insertPayslip(int payrollId, Payslip payslip) {
		Payroll payroll = payrollRepository.findById(payrollId)
				.orElseThrow(()-> new RuntimeException("Invalid Id given"));
		payslip.setPayroll(payroll);
		return payslipRepository.save(payslip);
	}

	public List<Payslip> getAll() {
		return payslipRepository.findAll();
	}

	public List<Payslip> getPayslipsByEmployeeId(int employeeId) {
        return payslipRepository.findByEmployeeId(employeeId);
    }
}



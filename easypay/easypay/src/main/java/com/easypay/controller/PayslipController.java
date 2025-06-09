package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Payslip;
import com.easypay.service.PayslipService;

@RestController
@RequestMapping("/api/payslip")
public class PayslipController {
	
	@Autowired
	private PayslipService payslipService;
	
	@PostMapping("/add/{payrollId}")
	public Payslip insertPayslip(@PathVariable int payrollId,
			                     @RequestBody Payslip payslip) {
		return payslipService.insertPayslip(payrollId,payslip);
	}
	
	@GetMapping("/get-all")
	public List<Payslip> getAll(){
		return payslipService.getAll();
	}
	
	@GetMapping("/get-all/{employeeId}")
	public List<Payslip> getPayslipsByEmployeeId(@PathVariable int employeeId){
		return payslipService.getPayslipsByEmployeeId(employeeId);
	}

}

package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Payroll;
import com.easypay.service.PayrollService;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "http://localhost:5173")
public class PayrollController {
	
	@Autowired
	private PayrollService payrollService;
	
	@PostMapping("/add/{employeeId}")
    public Payroll generatePayroll(@PathVariable int employeeId,
    		                     @RequestBody Payroll payroll) {
        return payrollService.generatePayroll(employeeId,payroll);
    }

    @GetMapping("/get-all")
    public List<Payroll> getAll() {
    	return payrollService.getAll();
    }
    
    @GetMapping("/get-one/{payrollId}")
    public Payroll getPayrollById(@PathVariable int payrollId) {
    	return payrollService.getPayrollById(payrollId);
    }
    
    @GetMapping("/get-all/{employeeId}")
    public List<Payroll> getAllPayrollsForEmployee(@PathVariable int employeeId){
    	return payrollService.getAllPayrollsForEmployee(employeeId);
    }

}

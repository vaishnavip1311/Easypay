package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Payroll> getAll(
    		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
    	return payrollService.getAll(page,size);
    }
    
    @GetMapping("/get-all-filtered")
    public List<Payroll> getFilteredPayrolls(@RequestParam String status){
    	return payrollService.getFilteredPayrolls(status);
    }
    
    @GetMapping("/get-one/{payrollId}")
    public Payroll getPayrollById(@PathVariable int payrollId) {
    	return payrollService.getPayrollById(payrollId);
    }
    
    @GetMapping("/get-all/{employeeId}")
    public List<Payroll> getAllPayrollsForEmployee(@PathVariable int employeeId,
    		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size){
    	return payrollService.getAllPayrollsForEmployee(employeeId,page,size);
    }
    
    

}

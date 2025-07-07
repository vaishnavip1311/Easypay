package com.easypay.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.PayrollPolicy;
import com.easypay.service.PayrollPolicyService;

@RestController
@RequestMapping("/api/payrollpolicy")
@CrossOrigin(origins = "http://localhost:5173")
public class PayrollPolicyController {
	
	@Autowired
	private PayrollPolicyService payrollPolicyService;
	
	@PostMapping("/add")
	public PayrollPolicy inserPayrollPolicy(@RequestBody PayrollPolicy payrollPolicy) {
		return payrollPolicyService.inserPayrollPolicy(payrollPolicy);
	}
	
	@GetMapping("/get-all")
	public List<PayrollPolicy> getAll(){
		return payrollPolicyService.getAll();
	}
	
	@GetMapping("/get-one")
	public PayrollPolicy getPayrollPolicy(@PathVariable int id) {
		return payrollPolicyService.getPayrollPolicy(id);
	}

	@PutMapping("/update/{id}")
	public PayrollPolicy updatePayrollPolicy(@PathVariable int id,@RequestBody PayrollPolicy updatedPayrollPolicy) {
		return payrollPolicyService.updatePayrollPolicy(id,updatedPayrollPolicy);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePayrollPolicy(@PathVariable int id){
		payrollPolicyService.deletePayrollPolicy(id);
		return ResponseEntity.status(HttpStatus.OK).body("Payroll Policy deleted with id "+id);
	}

}

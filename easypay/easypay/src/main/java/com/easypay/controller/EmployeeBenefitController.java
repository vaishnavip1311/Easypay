package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.EmployeeBenefit;
import com.easypay.service.EmployeeBenefitService;

@RestController
@RequestMapping("/api/employee-benefits")
public class EmployeeBenefitController {
	
	@Autowired
	private EmployeeBenefitService employeeBenefitService;
	
	@PostMapping("/assign/{employeeId}")
	public ResponseEntity<?> assignBenefitToEmployee(@PathVariable int employeeId) {
		
		employeeBenefitService.assignBenefitToEmployee(employeeId);
		return ResponseEntity.ok("Benefits assigned successfully based on employment type");
		
	}
	
	@GetMapping("/get-all/{employeeId}")
	public List<EmployeeBenefit> getBenefitForEmployee(@PathVariable int employeeId ) {
		return employeeBenefitService.getBenefitForEmployee(employeeId);
	}
	
	@PutMapping("/unassign/{employeeId}/{benefitId}")
	public ResponseEntity<?> unassignBenefitForEmployee(@PathVariable int employeeId, 
			                                            @PathVariable int benefitId){
		
		employeeBenefitService.unassignBenefitForEmployee(employeeId,benefitId);
		return ResponseEntity.ok("Benefit unassigned successfully.");
	}

}

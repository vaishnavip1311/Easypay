package com.easypay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.dto.EmployeeDashboardDTO;
import com.easypay.service.EmployeeDashboardService;

@RestController
@RequestMapping("/api/employee/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeDashboardController {
	
	@Autowired
	private EmployeeDashboardService employeeDashboardService;
	
	@GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDashboardDTO> getDashboardStats(@PathVariable int employeeId) {
        EmployeeDashboardDTO stats = employeeDashboardService.getDashboardStats(employeeId);
        return ResponseEntity.ok(stats);
    }

}

package com.easypay.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Employee;
import com.easypay.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
    private EmployeeService employeeService;
     
    @PostMapping("/add/{jobTitleId}")
    public Employee insertEmployee(@RequestBody Employee employee,@PathVariable int jobTitleId) {
        return employeeService.insertEmployee(employee,jobTitleId);
    }

    @GetMapping("/get-all")
    public List<Employee> getAll() {
    	return employeeService.getAll();
    }
    
    @GetMapping("/get-one")
    public Employee getEmployeeByUsername(Principal principal) {
    	String username = principal.getName(); 
    	return employeeService.getEmployeeByUsername(username);
    }
    
    @GetMapping("/get-one/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
    	return employeeService.getEmployeeById(employeeId);
    }
    

    
}

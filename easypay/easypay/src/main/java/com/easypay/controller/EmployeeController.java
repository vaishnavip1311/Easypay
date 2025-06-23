package com.easypay.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypay.model.Employee;
import com.easypay.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:5173")
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

    @PutMapping("/update/{employeeId}")
    public Employee updateEmploye(@PathVariable int employeeId,
    		                      @RequestBody Employee updatedemployee
    		                      ){
    	return employeeService.updateEmploye(employeeId,updatedemployee);
    }
    
    @PostMapping("/upload/profile-pic")
    public Employee uploadProfilePic(Principal principal,@RequestParam("file") MultipartFile file) throws IOException {
		return employeeService.uploadProfilePic(principal.getName(),file);
    	
    }
   
    
}

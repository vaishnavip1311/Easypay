package com.easypay.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/add/{jobTitleId}")
    public Employee insertEmployee(@RequestBody Employee employee, @PathVariable int jobTitleId) {
        logger.info("Inserting employee: {} with jobTitleId: {}", employee.getName(), jobTitleId);
        return employeeService.insertEmployee(employee, jobTitleId);
    }

    @GetMapping("/get-all")
    public List<Employee> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
        logger.info("Fetching all employees with page: {} and size: {}", page, size);
        return employeeService.getAll(page, size);
    }

    @GetMapping("/get-one")
    public Employee getEmployeeByUsername(Principal principal) {
        String username = principal.getName();
        logger.info("Fetching employee by username: {}", username);
        return employeeService.getEmployeeByUsername(username);
    }

    @GetMapping("/get-one/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
        logger.info("Fetching employee by ID: {}", employeeId);
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/update/{employeeId}")
    public Employee updateEmploye(@PathVariable int employeeId,
                                  @RequestBody Employee updatedemployee) {
        logger.info("Updating employee with ID: {}", employeeId);
        return employeeService.updateEmploye(employeeId, updatedemployee);
    }

    @PostMapping("/upload/profile-pic/{employeeId}")
    public Employee uploadProfilePic(@RequestParam("file") MultipartFile file,
                                     @PathVariable int employeeId) throws IOException {
        logger.info("Uploading profile picture for employee ID: {}", employeeId);
        return employeeService.uploadProfilePic(file, employeeId);
    }

    @PutMapping("update/profile-pic")
    public Employee updateProfilePic(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        logger.info("Updating profile picture for employee username: {}", username);
        return employeeService.updateProfilePic(username, file);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestParam String status) {
        logger.info("Updating status for employee ID: {} to status: {}", id, status);
        return employeeService.updateStatus(id, status);
    }
}
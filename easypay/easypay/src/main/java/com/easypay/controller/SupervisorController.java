package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Supervisor;
import com.easypay.service.SupervisorService;

@RestController
@RequestMapping("/api/supervisor")
public class SupervisorController {
	
	@Autowired
	private SupervisorService supervisorService;
	
	@PostMapping("/add")
    public Supervisor insertSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.insertSupervisor(supervisor);
    }

    @GetMapping("/get-all")
    public List<Supervisor> getAll() {
    	return supervisorService.getAll();
    }
    
    @GetMapping("/get-one/{supervisorId}")
    public Supervisor getSupervisorById(@PathVariable int supervisorId) {
    	return supervisorService.getSupervisorById(supervisorId);
    }

}

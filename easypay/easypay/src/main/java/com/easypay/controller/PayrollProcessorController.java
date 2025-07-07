package com.easypay.controller;

import java.io.IOException;
import java.security.Principal;

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

import com.easypay.model.PayrollProcessor;
import com.easypay.service.PayrollProcessorService;

@RestController
@RequestMapping("/api/payroll-processor")
@CrossOrigin(origins = "http://localhost:5173")
public class PayrollProcessorController {
	
	@Autowired
	private PayrollProcessorService payrollProcessorService;
	
	@PutMapping("/update/{processorId}")
    public PayrollProcessor updateProcessor(@PathVariable int processorId,
    		                      @RequestBody PayrollProcessor updatedProcessor
    		                      ){
    	return payrollProcessorService.updateEmploye(processorId,updatedProcessor);
    }
	
	@PostMapping("/upload/profile-pic")
    public PayrollProcessor uploadProfilePic(Principal principal,
    		@RequestParam("file") MultipartFile file) throws IOException {
		return payrollProcessorService.uploadProfilePic(principal.getName(),file);
    	
    }
	
	@GetMapping("/get-one")
	public PayrollProcessor getProcessorByUsername(Principal principal) {
		String username = principal.getName();
		
		return payrollProcessorService.getProcessorByUsername(username);
	}
}

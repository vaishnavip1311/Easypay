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

import com.easypay.model.HRManager;
import com.easypay.service.HRManagerService;

@RestController
@RequestMapping("/api/hrmanager")
@CrossOrigin(origins = "http://localhost:5173")
public class HRManagerController {
	
	@Autowired
	private HRManagerService hrManagerService;
	
	@PutMapping("/update/{hrId}")
    public HRManager updateEmploye(@PathVariable int hrId,
    		                      @RequestBody HRManager updatedHrManager
    		                      ){
    	return hrManagerService.updateEmploye(hrId,updatedHrManager);
    }
	
	@PostMapping("/upload/profile-pic")
    public HRManager uploadProfilePic(Principal principal,
    		@RequestParam("file") MultipartFile file) throws IOException {
		return hrManagerService.uploadProfilePic(principal.getName(),file);
    	
    }
	
	@GetMapping("/get-one")
	public HRManager getHrManagerByUsername(Principal principal) {
		String username = principal.getName();
		
		return hrManagerService.getHrManagerByUsername(username);
	}
	
	

}

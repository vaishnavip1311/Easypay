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

import com.easypay.model.JobTitle;
import com.easypay.service.JobTitleService;


@RestController
@RequestMapping("/api/jobtitle")
@CrossOrigin(origins = "http://localhost:5173")
public class JobTitleController {
	
	@Autowired
	private JobTitleService jobTitleService;
	
	@PostMapping("/add")
	public JobTitle addJobTitle(@RequestBody JobTitle jobTitle) {
		return jobTitleService.addJobTitle(jobTitle);
	}
	
	@GetMapping("/get-all")
	public List<JobTitle> getAllJobTitles(){
		return jobTitleService.getAllJobTitles();
	}
	
	@GetMapping("/get-one/{id}")
	public JobTitle getJobtitle(@PathVariable int id) {
		return jobTitleService.getJobtitle(id);
	}
	
	
	@PutMapping("/update/{id}")
	public JobTitle updateJobTitle(@PathVariable int id,
			                     @RequestBody JobTitle updatedJobTitle) {
		return jobTitleService.updateJobTitle(id,updatedJobTitle);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteJobtitle(@PathVariable int id){
		boolean deleted = jobTitleService.deleteJobtitle(id);
        if (deleted) {
            return ResponseEntity.ok("✅ Jobtitle deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Jobtitle not found.");
        }
	}

}

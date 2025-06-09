package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.JobTitle;
import com.easypay.service.JobTitleService;

@RestController
@RequestMapping("/api/jobtitle")
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

}

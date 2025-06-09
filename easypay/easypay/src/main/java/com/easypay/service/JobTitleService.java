package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.JobTitle;
import com.easypay.repository.JobTitleRepository;

@Service
public class JobTitleService {
	
	private JobTitleRepository jobTitleRepository;

	public JobTitleService(JobTitleRepository jobTitleRepository) {
		super();
		this.jobTitleRepository = jobTitleRepository;
	}

	public JobTitle addJobTitle(JobTitle jobTitle) {
		return jobTitleRepository.save(jobTitle);
	}

	public List<JobTitle> getAllJobTitles() {
		return jobTitleRepository.findAll();
	}

}

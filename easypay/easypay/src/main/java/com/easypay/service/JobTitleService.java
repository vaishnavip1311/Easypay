package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.exception.ResourceNotFoundException;
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

	public JobTitle updateJobTitle(int id, JobTitle updatedJobTitle) {
		JobTitle dbJobTitle = jobTitleRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Benefit not found id given is invalid."));
		
		if(updatedJobTitle.getTitleName() != null)
			dbJobTitle.setTitleName(updatedJobTitle.getTitleName());
		if(updatedJobTitle.getBasicSalary() != 0)
			dbJobTitle.setBasicSalary(updatedJobTitle.getBasicSalary());
		if(updatedJobTitle.getHraRate() != 0)
			dbJobTitle.setHraRate(updatedJobTitle.getHraRate());
		if(updatedJobTitle.getDaRate() != 0)
			dbJobTitle.setDaRate(updatedJobTitle.getDaRate());
		if(updatedJobTitle.getOtherAllowances() != 0)
			dbJobTitle.setOtherAllowances(updatedJobTitle.getOtherAllowances());
			
		return jobTitleRepository.save(dbJobTitle);
	}

	public boolean deleteJobtitle(int id) {
		if (jobTitleRepository.existsById(id)) {
            jobTitleRepository.deleteById(id);
            return true;
        }
        return false;
	}

	public JobTitle getJobtitle(int id) {
		return jobTitleRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Id given is invalid"));
	}

}

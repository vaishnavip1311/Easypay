package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.Benefit;
import com.easypay.repository.BenefitRepository;

@Service
public class BenefitService {
	
	private BenefitRepository benefitRepository;

	public BenefitService(BenefitRepository benefitRepository) {
		super();
		this.benefitRepository = benefitRepository;
	}

	public Benefit addBenefit(Benefit benefit) {
		return benefitRepository.save(benefit);
	}

	public List<Benefit> getAllBenefits() {
		return benefitRepository.findAll();
	}
	
	

}

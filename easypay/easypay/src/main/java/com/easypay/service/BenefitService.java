package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.exception.ResourceNotFoundException;
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

	public Benefit updateBenefit(int id, Benefit updatedBenefit) {
		
		Benefit dBenefit = benefitRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Benefit not found id given is invalid."));
		
		if(updatedBenefit.getBenefitName() != null)
			dBenefit.setBenefitName(updatedBenefit.getBenefitName());
		if(updatedBenefit.getBenefitType() != null)
			dBenefit.setBenefitType(updatedBenefit.getBenefitType());
		if(updatedBenefit.getAllowanceAmount() != 0)
			dBenefit.setAllowanceAmount(updatedBenefit.getAllowanceAmount());
		if(updatedBenefit.getDescription() != null)
			dBenefit.setDescription(updatedBenefit.getDescription());
		
		return benefitRepository.save(dBenefit);
	}

	public boolean deleteBenefitById(int id) {
		if (benefitRepository.existsById(id)) {
            benefitRepository.deleteById(id);
            return true;
        }
        return false;
	}

	public Benefit getBenefit(int id) {
		return benefitRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Id given invalid"));
	}
	
	

}

package com.easypay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Benefit;
import com.easypay.service.BenefitService;

@RestController
@RequestMapping("/api/benefit")
public class BenefitController {
	
	@Autowired
	private BenefitService benefitService;
	
	@PostMapping("/add")
	public Benefit addBenefit(@RequestBody Benefit benefit) {
		return benefitService.addBenefit(benefit);
	}
	
	@GetMapping("/get-all")
	public List<Benefit> getAllBenefits(){
		return benefitService.getAllBenefits();
	}


}

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

import com.easypay.model.Benefit;
import com.easypay.service.BenefitService;

@RestController
@RequestMapping("/api/benefit")
@CrossOrigin(origins = "http://localhost:5173")
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

	@PutMapping("/update/{id}")
	public Benefit updateBenefit(@PathVariable int id,
			                     @RequestBody Benefit updatedBenefit) {
		return benefitService.updateBenefit(id,updatedBenefit);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBenefit(@PathVariable int id){
		boolean deleted = benefitService.deleteBenefitById(id);
        if (deleted) {
            return ResponseEntity.ok("Benefit deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Benefit not found.");
        }
	}
	
	@GetMapping("/get-one/{id}")
	public Benefit getBenefit(@PathVariable int id) {
		return benefitService.getBenefit(id);
	}

}

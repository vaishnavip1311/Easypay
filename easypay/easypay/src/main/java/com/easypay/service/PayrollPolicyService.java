package com.easypay.service;
import com.easypay.repository.PayrollPolicyRepository;
import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.PayrollPolicy;

@Service
public class PayrollPolicyService {

    private PayrollPolicyRepository payrollPolicyRepository;
   
	public PayrollPolicyService(PayrollPolicyRepository payrollPolicyRepository) {
		super();
		this.payrollPolicyRepository = payrollPolicyRepository;
	}

	public PayrollPolicy inserPayrollPolicy(PayrollPolicy payrollPolicy) {
		return payrollPolicyRepository.save(payrollPolicy);
	}

	public List<PayrollPolicy> getAll() {
		return payrollPolicyRepository.findAll();
	}

	public PayrollPolicy updatePayrollPolicy(int id, PayrollPolicy updatedPayrollPolicy) {
		PayrollPolicy dbPayrollPolicy =payrollPolicyRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Invalid id given"));
		
		if(updatedPayrollPolicy.getName() != null)
			dbPayrollPolicy.setName(updatedPayrollPolicy.getName());
		if(updatedPayrollPolicy.getType() != null)
			dbPayrollPolicy.setType(updatedPayrollPolicy.getType());
		if(updatedPayrollPolicy.getDescription() != null)
			dbPayrollPolicy.setDescription(updatedPayrollPolicy.getDescription());
		if(updatedPayrollPolicy.getEffectiveFrom() != null)
			dbPayrollPolicy.setEffectiveFrom(updatedPayrollPolicy.getEffectiveFrom());
		if(updatedPayrollPolicy.getStatus() !=null)
			dbPayrollPolicy.setStatus(updatedPayrollPolicy.getStatus());
		
		return payrollPolicyRepository.save(dbPayrollPolicy);
	}

	public void deletePayrollPolicy(int id) {
		payrollPolicyRepository.deleteById(id);
	}

	public PayrollPolicy getPayrollPolicy(int id) {
		return payrollPolicyRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Id given is invalid"));
	}
	

}

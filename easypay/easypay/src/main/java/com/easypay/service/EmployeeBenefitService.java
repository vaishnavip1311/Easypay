package com.easypay.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.Benefit;
import com.easypay.model.Employee;
import com.easypay.model.EmployeeBenefit;
import com.easypay.repository.BenefitRepository;
import com.easypay.repository.EmployeeBenefitRepository;
import com.easypay.repository.EmployeeRepository;

@Service
public class EmployeeBenefitService {
	
	private EmployeeBenefitRepository employeeBenefitRepository;
	private EmployeeRepository employeeRepository;
	private BenefitRepository benefitRepository;

	public EmployeeBenefitService(EmployeeBenefitRepository employeeBenefitRepository,
			EmployeeRepository employeeRepository, BenefitRepository benefitRepository) {
		super();
		this.employeeBenefitRepository = employeeBenefitRepository;
		this.employeeRepository = employeeRepository;
		this.benefitRepository = benefitRepository;
	}

	public void assignBenefitToEmployee(int employeeId) {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()->new RuntimeException("Invalid id given"));
		
		String type = employee.getEmployementType().toLowerCase();

	    List<Benefit> allBenefits = benefitRepository.findAll();
	    List<Benefit> assignedBenefits = new ArrayList<>();

	    switch (type) {
	        case "full-time":
	            assignedBenefits.addAll(allBenefits);
	            break;

	        case "part-time":
	            assignedBenefits = allBenefits.stream()
	                .filter(b -> List.of("Internet Reimbursement", "Meal Card Allowance", "Fitness/Gym Membership", "Learning & Development")
	                        .contains(b.getBenefitName()))
	                .toList();
	            break;

	        case "contract":
	            assignedBenefits = allBenefits.stream()
	                .filter(b -> List.of("Internet Reimbursement", "Work-from-Home Setup")
	                        .contains(b.getBenefitName()))
	                .toList();
	            break;

	        case "intern":
	            assignedBenefits = allBenefits.stream()
	                .filter(b -> b.getBenefitName().equals("Learning & Development"))
	                .toList();
	            break;

	        default:
	            throw new IllegalArgumentException("Unknown employment type: " + type);
	    }
	    
	    for (Benefit benefit : assignedBenefits) {
	        EmployeeBenefit eb = new EmployeeBenefit();
	        eb.setEmployee(employee);
	        eb.setBenefit(benefit);
	        eb.setStatus("active");
	        eb.setAssignedDate(LocalDate.now().toString());

	        employeeBenefitRepository.save(eb);
	    }

	}

	public List<EmployeeBenefit> getBenefitForEmployee(int employeeId) {
		return employeeBenefitRepository.getBenefitsByEmployeeId(employeeId);
	}

	public void unassignBenefitForEmployee(int employeeId, int benefitId) {
		int updated = employeeBenefitRepository.unassignBenefit(employeeId, benefitId);
	    if (updated == 0) {
	        throw new RuntimeException("No active benefit found for employee.");
	    }
	}

}

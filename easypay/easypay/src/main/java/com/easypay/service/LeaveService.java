package com.easypay.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.Employee;
import com.easypay.model.Leave;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.LeaveRepository;

@Service
public class LeaveService {
	
	private LeaveRepository leaveRepository;
	private EmployeeRepository employeeRepository;

	public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository) {
		super();
		this.leaveRepository = leaveRepository;
		this.employeeRepository = employeeRepository;
	}

	public Leave insertLeave(int employeeId, Leave leave) {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()->new RuntimeException("Invalid Id given"));
		
		leave.setEmployee(employee);
		leave.setStatus("Pending");
		return leaveRepository.save(leave);
	}

	public List<Leave> getAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return leaveRepository.findAll(pageable).getContent();
	}

	public Leave getLeaveById(int leaveId) {
		return leaveRepository.findById(leaveId)
				.orElseThrow(()->new RuntimeException("Invalid id given"));
	}

	public Leave updateLeaveStatus(int leaveId, String status) {
		Leave leave = leaveRepository.findById(leaveId)
				.orElseThrow(() -> new RuntimeException("Leave not found"));
		
		if (!status.equalsIgnoreCase("APPROVED") && !status.equalsIgnoreCase("REJECTED")) {
	        throw new IllegalArgumentException("Status must be APPROVED or REJECTED");
	    }
		
		leave.setStatus(status);
		
		return leaveRepository.save(leave);
	}

	public List<Leave> getAllLeavesByEmployeeId(int employeeId,int page, int size) {
		employeeRepository.findById(employeeId)
		            .orElseThrow(()->new ResourceNotFoundException("Employee not found.  Id given is invalid"));
		Pageable pageable = PageRequest.of(page, size);
		return leaveRepository.getAllLeavesByEmployeeId(employeeId,pageable);
	}

}

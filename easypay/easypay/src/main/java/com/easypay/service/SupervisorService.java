package com.easypay.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easypay.model.Supervisor;
import com.easypay.model.User;
import com.easypay.repository.SupervisorRepository;

@Service
public class SupervisorService {
	
	private SupervisorRepository supervisorRepository;
	private UserService userService;

	public SupervisorService(SupervisorRepository supervisorRepository,UserService userService) {
		super();
		this.supervisorRepository = supervisorRepository;
		this.userService = userService;
	}

	public Supervisor insertSupervisor(Supervisor supervisor) {
		User user = supervisor.getUser();
		user.setRole("SUPERVISOR");
		user = userService.addUser(user);
		supervisor.setUser(user);
		
		return supervisorRepository.save(supervisor);
	}

	public List<Supervisor> getAll() {
		return supervisorRepository.findAll();
	}

	public Supervisor getSupervisorById(int supervisorId) {
		return supervisorRepository.findById(supervisorId)
				.orElseThrow(()-> new RuntimeException("Invalid id given"));
	}

}

package com.easypay.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.easypay.model.Employee;
import com.easypay.model.User;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private EmployeeRepository employeeRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			EmployeeRepository employeeRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.employeeRepository = employeeRepository;
	}

	public User addUser(User user) {
		// encrypt the plain text password given 
		String plainPassword = user.getPassword(); //<- this gives you plain password
		String encodedPassword =  passwordEncoder.encode(plainPassword);
		user.setPassword(encodedPassword); //<- Now, User has encoded password 
		
		// Save User in DB 
		return userRepository.save(user);
	}

	public ResponseEntity<?> deleteUser(int id) {
		userRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted with id " + id);
	}

	public Object getUserInfo(String username) {
		User user = userRepository.findByUsername(username);
		switch (user.getRole().toUpperCase()) {
		case "EMPLOYEE":
			Employee employee = employeeRepository.getEmployeeByUsername(username);
			return employee;
			
		default:
			return null;
		}
	}

}

/*
public Object getUserInfo(String username) {
		User user = userRepository.findByUsername(username);
		switch (user.getRole().toUpperCase()) {
			case "LEARNER":
				Learner learner = learnerRepository.getLearnerByUsername(username);
				return learner;
			case "AUTHOR":
				Author author = authorRepository.getAuthorByUsername(username);
				if (author.isActive())
					return author;
				else
					throw new RuntimeException("Author Inactive");
			case "EXECUTIVE":
				return null;
			default:
				return null;
		}
	}
*/
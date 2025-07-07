package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.PayrollProcessor;

public interface PayrollProcessorRepository extends JpaRepository<PayrollProcessor, Integer>{

	@Query("select p from PayrollProcessor p where p.user.username=?1")
	PayrollProcessor findProcessorByUsername(String username);
	
	

}

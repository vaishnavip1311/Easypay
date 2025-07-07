package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.HRManager;

public interface HRManagerRepository extends JpaRepository<HRManager, Integer>{
	
	@Query("select h from HRManager h where h.user.username=?1")
	HRManager getHrManagerByUsername(String username);

}

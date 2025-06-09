package com.easypay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypay.model.PayrollPolicy;

public interface PayrollPolicyRepository extends JpaRepository<PayrollPolicy, Integer>{
	
	@Query("SELECT p FROM PayrollPolicy p WHERE p.status = 'ACTIVE' ")
    Optional<PayrollPolicy> findActivePolicyForEmployee(@Param("employeeId") int employeeId);

}

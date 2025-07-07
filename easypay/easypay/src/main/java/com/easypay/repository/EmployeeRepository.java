package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.easypay.model.Employee;

import jakarta.transaction.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	@Query("select e from Employee e where e.user.username=?1")
	Employee getEmployeeByUsername(String username);

	
	@Modifying
	@Transactional
	@Query("UPDATE Employee e SET e.status = ?2 WHERE e.id = ?1")
	int updateStatus(int id , String status);
	
	

}

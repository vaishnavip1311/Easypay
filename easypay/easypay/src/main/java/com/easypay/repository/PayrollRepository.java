package com.easypay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer>{

	@Query("select p from Payroll p where p.employee.id=?1")
	List<Payroll> getAllPayrollsForEmployee(int employeeId);

}

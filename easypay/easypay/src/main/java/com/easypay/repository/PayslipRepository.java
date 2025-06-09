package com.easypay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypay.model.Payslip;

public interface PayslipRepository extends JpaRepository<Payslip, Integer>{

	@Query("SELECT p FROM Payslip p WHERE p.payroll.employee.id = :employeeId")
	List<Payslip> findByEmployeeId(@Param("employeeId") int employeeId);

}

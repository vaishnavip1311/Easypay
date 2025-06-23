package com.easypay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>{
	
	@Query("SELECT l FROM Leave l WHERE l.employee.id = ?1 " +
	           "AND l.leaveType = 'UNPAID' " +
	           "AND l.status = 'APPROVED' " +
	           "AND (l.endDate <= ?3 AND  l.startDate >= ?2)")
	List<Leave> findApprovedUnpaidLeavesWithinPeriod(int employeeId, LocalDate startDate, LocalDate endDate);

	
	@Query("select l from Leave l where l.employee.id = ?1")
	List<Leave> getAllLeavesByEmployeeId(int employeeId);

}

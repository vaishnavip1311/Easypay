package com.easypay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.easypay.model.EmployeeBenefit;

import jakarta.transaction.Transactional;

public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit, Integer>{

	@Query("select eb from EmployeeBenefit eb where eb.employee.id=?1")
	List<EmployeeBenefit> getBenefitsByEmployeeId(int employeeId);

	@Modifying
    @Transactional
	@Query("update EmployeeBenefit eb set eb.status = 'inactive' where eb.employee.id=?1 and eb.benefit.id=?2 ")
	int unassignBenefit(@Param("employeeId") int employeeId, @Param("benefitId") int benefitId);

	@Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.employee.id = ?1 AND eb.status = 'ACTIVE'")
    List<EmployeeBenefit> findActiveBenefitsByEmployeeId(int employeeId);
}

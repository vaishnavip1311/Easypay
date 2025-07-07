package com.easypay.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer>{

	@Query("select p from Payroll p where p.employee.id=?1 ORDER BY p.payPeriod DESC")
	List<Payroll> getAllPayrollsForEmployee(int employeeId,Pageable pageable);
	
	@Query("SELECT SUM(p.netSalary) FROM Payroll p WHERE p.employee.id = ?1 AND FUNCTION('MONTH', p.payPeriod) = ?2 AND FUNCTION('YEAR', p.payPeriod) = ?3")
    Double getMonthlyEarnings(int employeeId, int month,  int year);

    @Query("SELECT SUM(p.netSalary) FROM Payroll p WHERE p.employee.id = ?1 AND FUNCTION('YEAR', p.payPeriod) = ?2")
    Double getYtdEarnings(int employeeId,int year);

    @Query("SELECT p FROM Payroll p WHERE p.employee.id = ?1  ORDER BY p.payPeriod DESC ")
    List<Payroll> findRecentPayrolls(int employeeId, Pageable pageable);

    @Query("SELECT FUNCTION('MONTH', p.payPeriod), SUM(p.netSalary) " +
    	       "FROM Payroll p WHERE p.employee.id = ?1 AND FUNCTION('YEAR', p.payPeriod) = ?2 " +
    	       "GROUP BY FUNCTION('MONTH', p.payPeriod) ORDER BY FUNCTION('MONTH', p.payPeriod)")
    List<Object[]> getMonthlyEarningsTrend(int employeeId, int year);
    
    
    @Query("SELECT SUM(p.netSalary) FROM Payroll p WHERE FUNCTION('MONTH', p.payPeriod) = ?1 AND FUNCTION('YEAR', p.payPeriod) = ?2")
    Double getTotalPayrollThisMonth(int month, int year);

    @Query("SELECT COUNT(p) FROM Payroll p")
    int countPayrolls();

    @Query("SELECT COUNT(p) FROM Payroll p WHERE p.status = 'Verified'")
    int countVerifiedPayrolls();

    @Query("SELECT MONTH(p.payPeriod), SUM(p.netSalary) FROM Payroll p WHERE YEAR(p.payPeriod) = ?1 GROUP BY MONTH(p.payPeriod)")
    List<Object[]> getMonthlyPayrolls(int year);

    @Query("select p from Payroll p where status = ?1")
	List<Payroll> getFilteredPayrolls(String status);

    @Query("select p from Payroll p ORDER BY p.payPeriod DESC")
	List<Payroll> findAllPayrolls(Pageable pageable);

}

package com.easypay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.easypay.model.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{

	@Query("select t from Timesheet t where t.employee.id=?1 order by t.weekStart desc,t.date asc ")
	List<Timesheet> geTimesheetsByEmployeeId(int employeeId);

	@Query("select t from Timesheet t where t.employee.id=?1 and t.weekStart=?2")
	List<Timesheet> getTimesheetsByWeekstartDate(int employeeId, LocalDate weekStart);

}

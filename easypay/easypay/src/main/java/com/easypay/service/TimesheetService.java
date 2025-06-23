package com.easypay.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easypay.exception.ResourceNotFoundException;
import com.easypay.model.Employee;
import com.easypay.model.Timesheet;
import com.easypay.repository.EmployeeRepository;
import com.easypay.repository.TimesheetRepository;

@Service
public class TimesheetService {
	
	private TimesheetRepository timesheetRepository;
	private EmployeeRepository employeeRepository;

	public TimesheetService(TimesheetRepository timesheetRepository, EmployeeRepository employeeRepository) {
		super();
		this.timesheetRepository = timesheetRepository;
		this.employeeRepository = employeeRepository;
	}

	public ResponseEntity<?> submitTimesheet(List<Timesheet> timesheets, int employeeId) {
		
		if (timesheets.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty timesheet submitted.");
        }

        for (Timesheet entry : timesheets) {
            

            Optional<Employee> optionalEmp = employeeRepository.findById(employeeId);
            if (optionalEmp.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee ID: " + employeeId);
            }

            // Re-attach valid Employee from DB
            entry.setEmployee(optionalEmp.get());

            // Auto-calculate hours worked
            long minutes = ChronoUnit.MINUTES.between(entry.getClockIn(), entry.getClockOut());
            entry.setHoursWorked(minutes / 60.0);

            entry.setStatus("Submitted");
            timesheetRepository.save(entry);
        }

        return ResponseEntity.ok("Timesheet submitted successfully.");
	}

	public ResponseEntity<?> geTimesheetsByEmployeeId(int employeeId) {
		employeeRepository.findById(employeeId).
		                orElseThrow(()->new ResourceNotFoundException("Employee not found,Id given is invalid"));
		
		List<Timesheet> timesheets = timesheetRepository.geTimesheetsByEmployeeId(employeeId);

	    if (timesheets.isEmpty()) {
	        return ResponseEntity.ok("No timesheet history found.");
	    }
		return ResponseEntity.ok(timesheets);
	}

	public List<Timesheet> getTimesheetsByWeekstartDate(int employeeId, LocalDate weekStart) {
		employeeRepository.findById(employeeId).
        orElseThrow(()->new ResourceNotFoundException("Employee not found,Id given is invalid"));

		return timesheetRepository.getTimesheetsByWeekstartDate(employeeId,weekStart);
	}
	
	

}

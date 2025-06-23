package com.easypay.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.model.Timesheet;
import com.easypay.service.TimesheetService;

@RestController
@RequestMapping("/api/timesheet")
@CrossOrigin(origins = "http://localhost:5173")
public class TimesheetController {
	
	@Autowired
	private TimesheetService timesheetService;
	
	@PostMapping("/add/{employeeId}")
	public ResponseEntity<?> submitTimesheet(@RequestBody List<Timesheet> timesheets,
			                                 @PathVariable int employeeId){
		return timesheetService.submitTimesheet(timesheets,employeeId);
	}
	
	@GetMapping("/get-all/{employeeId}")
	public ResponseEntity<?> geTimesheetsByEmployeeId(@PathVariable int employeeId){
		return timesheetService.geTimesheetsByEmployeeId(employeeId);
	}
	
	@GetMapping("/get-by-weekstart/{employeeId}")
	public List<Timesheet> getTimesheetsByWeekstartDate(@PathVariable int employeeId
			,@RequestParam LocalDate weekStart){
		return timesheetService.getTimesheetsByWeekstartDate(employeeId,weekStart);
	}
	
	

}

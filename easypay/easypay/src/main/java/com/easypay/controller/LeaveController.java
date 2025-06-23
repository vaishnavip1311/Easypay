package com.easypay.controller;

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

import com.easypay.model.Leave;
import com.easypay.service.LeaveService;

@RestController
@RequestMapping("/api/leave")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;

	@PostMapping("/add/{employeeId}")
    public Leave insertLeave(@PathVariable int employeeId,
    		                 @RequestBody Leave leave) {
        return leaveService.insertLeave(employeeId,leave);
    }

    @GetMapping("/get-all")
    public List<Leave> getAll() {
    	return leaveService.getAll();
    }
    
    @GetMapping("/get-one/{leaveId}")
    public Leave getLeaveById(@PathVariable int leaveId) {
    	return leaveService.getLeaveById(leaveId);
    }
    
    @PostMapping("/{leaveId}/status")
    public ResponseEntity<Leave> updateLeaveStatus(@PathVariable int leaveId,
                                                   @RequestParam String status) {

        Leave updatedLeave = leaveService.updateLeaveStatus(leaveId, status);
        return ResponseEntity.ok(updatedLeave);
    }
    
    @GetMapping("/get-all/{employeeId}")
    public List<Leave> getAllLeavesByEmployeeId(@PathVariable int employeeId){
    	return leaveService.getAllLeavesByEmployeeId(employeeId);
    }

}

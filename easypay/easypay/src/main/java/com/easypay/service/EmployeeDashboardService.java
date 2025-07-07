package com.easypay.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.easypay.dto.EmployeeDashboardDTO;
import com.easypay.model.Payroll;
import com.easypay.repository.LeaveRepository;
import com.easypay.repository.PayrollRepository;

@Service
public class EmployeeDashboardService {
	
	private PayrollRepository payrollRepository;
	private LeaveRepository leaveRepository;

	public EmployeeDashboardService(PayrollRepository payrollRepository, LeaveRepository leaveRepository) {
		super();
		this.payrollRepository = payrollRepository;
		this.leaveRepository = leaveRepository;
	}


	public EmployeeDashboardDTO getDashboardStats(int employeeId) {
	    EmployeeDashboardDTO dto = new EmployeeDashboardDTO();

	    int month = LocalDate.now().getMonthValue();
	    int year = LocalDate.now().getYear();

	    // Handle possible nulls from repository
	    Double monthly = payrollRepository.getMonthlyEarnings(employeeId, month, year);
	    dto.setMonthlyEarnings(monthly != null ? monthly : 0.0);

	    Double ytd = payrollRepository.getYtdEarnings(employeeId, year);
	    dto.setYtdEarnings(ytd != null ? ytd : 0.0);

	    Integer leavesTaken = leaveRepository.getLeavesTaken(employeeId, year);
	    dto.setLeavesTaken(leavesTaken != null ? leavesTaken : 0);
	    dto.setLeavesAvailable(12 - dto.getLeavesTaken());

	    Integer pendingRequests = leaveRepository.getPendingLeaveRequests(employeeId);
	    dto.setPendingLeaveRequests(pendingRequests != null ? pendingRequests : 0);

	    // Last payment
	    List<Payroll> recent = payrollRepository.findRecentPayrolls(employeeId, PageRequest.of(0, 1));
	    if (!recent.isEmpty()) {
	        Payroll last = recent.get(0);
	        dto.setLastPaymentAmount(last.getNetSalary());
	        dto.setLastPaymentDate(last.getProcessedOn());
	    }

	    dto.setWorkingDaysLogged(18); // static or fetch dynamically

	    // Earnings Trend
	    List<Object[]> earningsData = payrollRepository.getMonthlyEarningsTrend(employeeId, year);
	    List<EmployeeDashboardDTO.EarningsChartPoint> earningsTrend = new ArrayList<>();
	    for (Object[] row : earningsData) {
	        int earningMonth = ((Number) row[0]).intValue(); // fix from parsing string to casting number
	        double earning = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;

	        earningsTrend.add(new EmployeeDashboardDTO.EarningsChartPoint(
	            Month.of(earningMonth).name(), earning
	        ));
	    }
	    dto.setEarningsTrend(earningsTrend);

	    // Leave Breakdown
	    List<Object[]> leaveData = leaveRepository.getLeaveBreakdown(employeeId);
	    List<EmployeeDashboardDTO.LeaveBreakdown> leaveChart = new ArrayList<>();
	    for (Object[] row : leaveData) {
	        leaveChart.add(new EmployeeDashboardDTO.LeaveBreakdown(
	            (String) row[0], ((Number) row[1]).intValue()
	        ));
	    }
	    dto.setLeaveBreakdown(leaveChart);

	    dto.setNextPayDate(LocalDate.now().plusMonths(1).withDayOfMonth(1).toString());

	    return dto;
	}


}

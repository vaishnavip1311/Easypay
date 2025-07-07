package com.easypay.dto;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDashboardDTO {

    private double monthlyEarnings;
    private double ytdEarnings;
    private int leavesAvailable;
    private int leavesTaken;
    private String nextPayDate;
    private double lastPaymentAmount;
    private LocalDate lastPaymentDate;
    private int pendingLeaveRequests;
    private int workingDaysLogged;

    private List<EarningsChartPoint> earningsTrend;
    private List<LeaveBreakdown> leaveBreakdown;
    
    
    public static class EarningsChartPoint {
        private String month;
        private double earnings;
        // Getters & Setters
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public double getEarnings() {
			return earnings;
		}
		public void setEarnings(double earnings) {
			this.earnings = earnings;
		}
		
		public EarningsChartPoint(String month, double earnings) {
            this.month = month;
            this.earnings = earnings;
        }
    }

    
    public static class LeaveBreakdown {
        private String type;
        private int count;
        // Getters & Setters
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public LeaveBreakdown(String type, int count) {
            this.type = type;
            this.count = count;
        }
    }


    // Getters & Setters
	public double getMonthlyEarnings() {
		return monthlyEarnings;
	}


	public void setMonthlyEarnings(double monthlyEarnings) {
		this.monthlyEarnings = monthlyEarnings;
	}


	public double getYtdEarnings() {
		return ytdEarnings;
	}


	public void setYtdEarnings(double ytdEarnings) {
		this.ytdEarnings = ytdEarnings;
	}


	public int getLeavesAvailable() {
		return leavesAvailable;
	}


	public void setLeavesAvailable(int leavesAvailable) {
		this.leavesAvailable = leavesAvailable;
	}


	public int getLeavesTaken() {
		return leavesTaken;
	}


	public void setLeavesTaken(int leavesTaken) {
		this.leavesTaken = leavesTaken;
	}


	public String getNextPayDate() {
		return nextPayDate;
	}


	public void setNextPayDate(String nextPayDate) {
		this.nextPayDate = nextPayDate;
	}


	public double getLastPaymentAmount() {
		return lastPaymentAmount;
	}


	public void setLastPaymentAmount(double lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}


	public LocalDate getLastPaymentDate() {
		return lastPaymentDate;
	}


	public void setLastPaymentDate(LocalDate lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}


	public int getPendingLeaveRequests() {
		return pendingLeaveRequests;
	}


	public void setPendingLeaveRequests(int pendingLeaveRequests) {
		this.pendingLeaveRequests = pendingLeaveRequests;
	}


	public int getWorkingDaysLogged() {
		return workingDaysLogged;
	}


	public void setWorkingDaysLogged(int workingDaysLogged) {
		this.workingDaysLogged = workingDaysLogged;
	}


	public List<EarningsChartPoint> getEarningsTrend() {
		return earningsTrend;
	}


	public void setEarningsTrend(List<EarningsChartPoint> earningsTrend) {
		this.earningsTrend = earningsTrend;
	}


	public List<LeaveBreakdown> getLeaveBreakdown() {
		return leaveBreakdown;
	}


	public void setLeaveBreakdown(List<LeaveBreakdown> leaveBreakdown) {
		this.leaveBreakdown = leaveBreakdown;
	}

    
}
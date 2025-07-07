package com.easypay.dto;

import java.util.Map;

public class HrDashboardDTO {
	    private int totalEmployees;
	    private int activePolicies;
	    private double monthlyPayrollProcessed;
	    private Map<String, Integer> employeeTypeDistribution; // Full-Time, Contract, etc.

	    // Getters and setters
	    public int getTotalEmployees() {
	        return totalEmployees;
	    }

	    public void setTotalEmployees(int totalEmployees) {
	        this.totalEmployees = totalEmployees;
	    }

	    public int getActivePolicies() {
	        return activePolicies;
	    }

	    public void setActivePolicies(int activePolicies) {
	        this.activePolicies = activePolicies;
	    }

	    public double getMonthlyPayrollProcessed() {
	        return monthlyPayrollProcessed;
	    }

	    public void setMonthlyPayrollProcessed(double monthlyPayrollProcessed) {
	        this.monthlyPayrollProcessed = monthlyPayrollProcessed;
	    }

	    public Map<String, Integer> getEmployeeTypeDistribution() {
	        return employeeTypeDistribution;
	    }

	    public void setEmployeeTypeDistribution(Map<String, Integer> employeeTypeDistribution) {
	        this.employeeTypeDistribution = employeeTypeDistribution;
	    }

}

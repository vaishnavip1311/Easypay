package com.easypay.dto;

import java.util.List;
import java.util.Map;

public class PayrollProcessorDashboardDTO {
    private int payrollsCalculated;
    private int verifiedPayrolls;
    private int totalBenefits;
    private List<Double> monthlyPayrolls;
    private List<String> months;
    private Map<String, Double> benefitBreakdown;
    
    // Getters and setters
    
	public int getPayrollsCalculated() {
		return payrollsCalculated;
	}
	public void setPayrollsCalculated(int payrollsCalculated) {
		this.payrollsCalculated = payrollsCalculated;
	}
	public int getVerifiedPayrolls() {
		return verifiedPayrolls;
	}
	public void setVerifiedPayrolls(int verifiedPayrolls) {
		this.verifiedPayrolls = verifiedPayrolls;
	}

	public List<Double> getMonthlyPayrolls() {
		return monthlyPayrolls;
	}
	public void setMonthlyPayrolls(List<Double> monthlyPayrolls) {
		this.monthlyPayrolls = monthlyPayrolls;
	}
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	public Map<String, Double> getBenefitBreakdown() {
		return benefitBreakdown;
	}
	public void setBenefitBreakdown(Map<String, Double> benefitBreakdown) {
		this.benefitBreakdown = benefitBreakdown;
	}
	public int getTotalBenefits() {
		return totalBenefits;
	}
	public void setTotalBenefits(int totalBenefits) {
		this.totalBenefits = totalBenefits;
	}
	

}
package com.easypay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payroll")
public class Payroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Employee employee;
	
	@Column(name = "pay_period")
	private String payPeriod;
	
	@Column(name = "basic_salary")
	private double basicSalary;
	
	@Column(name = "hra")
	private double hra;
	
	@Column(name = "da")
	private double da;
	
	@Column(name = "other_allowances")
	private double otherAllowances;
	
	@Column(name = "tax_deduction")
	private double taxDeduction;
	
	@Column(name = "pf_contribution")
	private double pfContribution;
	
	@Column(name = "unpaid_leave_deduction")
	private double unpaidLeaveDeduction;
	
	@Column(name = "total_earnings")
	private double totalEarnings;
	
	@Column(name = "total_deductions")
	private double totalDeductions;
	
	@Column(name = "net_salary")
	private double netSalary;     // final salary
	
	private String status;
	
	@Column(name = "processed_on")
	private String processedOn;
	
	//getters and setters 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public double getHra() {
		return hra;
	}

	public void setHra(double hra) {
		this.hra = hra;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public double getOtherAllowances() {
		return otherAllowances;
	}

	public void setOtherAllowances(double otherAllowances) {
		this.otherAllowances = otherAllowances;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public void setTaxDeduction(double taxDeduction) {
		this.taxDeduction = taxDeduction;
	}

	public double getPfContribution() {
		return pfContribution;
	}

	public void setPfContribution(double pfContribution) {
		this.pfContribution = pfContribution;
	}

	public double getUnpaidLeaveDeduction() {
		return unpaidLeaveDeduction;
	}

	public void setUnpaidLeaveDeduction(double unpaidLeaveDeduction) {
		this.unpaidLeaveDeduction = unpaidLeaveDeduction;
	}

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public double getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessedOn() {
		return processedOn;
	}

	public void setProcessedOn(String processedOn) {
		this.processedOn = processedOn;
	}

}

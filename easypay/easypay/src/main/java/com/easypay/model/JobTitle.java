package com.easypay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_title")
public class JobTitle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "title_name")
	private String titleName;
	@Column(name = "basic_salary")
	private double basicSalary;
	private double hraRate;    //house rent allowance
	private double daRate;       // dearness allowance
	private double otherAllowances;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public double getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public double getHraRate() {
		return hraRate;
	}
	public void setHraRate(double hraRate) {
		this.hraRate = hraRate;
	}
	public double getDaRate() {
		return daRate;
	}
	public void setDaRate(double daRate) {
		this.daRate = daRate;
	}
	public double getOtherAllowances() {
		return otherAllowances;
	}
	public void setOtherAllowances(double otherAllowances) {
		this.otherAllowances = otherAllowances;
	}

}

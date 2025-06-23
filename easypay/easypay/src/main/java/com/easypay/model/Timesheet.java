package com.easypay.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timesheet")
public class Timesheet {
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private Employee employee;
	
	private String day;
	
	private LocalDate date;

	@Column(name = "clock_in")
    private LocalTime clockIn;
	
	@Column(name = "clock_out")
    private LocalTime clockOut;
	
	@Column(name = "hours_worked")
	private double hoursWorked;
	
	@Column(name = "week_start")
	private LocalDate weekStart;
	
	private String status;

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getClockIn() {
		return clockIn;
	}

	public void setClockIn(LocalTime clockIn) {
		this.clockIn = clockIn;
	}

	public LocalTime getClockOut() {
		return clockOut;
	}

	public void setClockOut(LocalTime clockOut) {
		this.clockOut = clockOut;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public LocalDate getWeekStart() {
		return weekStart;
	}

	public void setWeekStart(LocalDate weekStart) {
		this.weekStart = weekStart;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}

package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypay.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Integer>{

}

package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypay.model.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer>{

}

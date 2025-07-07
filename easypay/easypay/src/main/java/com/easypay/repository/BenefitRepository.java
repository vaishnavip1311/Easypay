package com.easypay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Integer>{

    @Query("SELECT b.benefitType, SUM(b.allowanceAmount) FROM Benefit b GROUP BY b.benefitType")
    List<Object[]> getBenefitBreakdown();

    @Query("SELECT COUNT(b) FROM Benefit b")
    int countBenefits();
}

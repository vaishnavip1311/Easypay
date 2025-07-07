package com.easypay.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.easypay.dto.PayrollProcessorDashboardDTO;
import com.easypay.repository.BenefitRepository;
import com.easypay.repository.PayrollRepository;

@Service
public class PayrollProcessorDashboardService {

    private final PayrollRepository payrollRepository;
    private final BenefitRepository benefitRepository;

    public PayrollProcessorDashboardService(PayrollRepository payrollRepository, BenefitRepository benefitRepository) {
        this.payrollRepository = payrollRepository;
        this.benefitRepository = benefitRepository;
    }

    public PayrollProcessorDashboardDTO getDashboardStats() {
        PayrollProcessorDashboardDTO dto = new PayrollProcessorDashboardDTO();

        int currentYear = LocalDate.now().getYear();

        dto.setPayrollsCalculated(payrollRepository.countPayrolls());
        dto.setVerifiedPayrolls(payrollRepository.countVerifiedPayrolls());
        dto.setTotalBenefits(benefitRepository.countBenefits());

        // Monthly payroll data
        List<Object[]> monthlyData = payrollRepository.getMonthlyPayrolls(currentYear);
        List<Double> monthlyPayrolls = new ArrayList<>();
        List<String> months = new ArrayList<>();

        for (Object[] row : monthlyData) {
            Integer monthNumber = (Integer) row[0];
            Double total = (Double) row[1];

            months.add(Month.of(monthNumber).name());
            monthlyPayrolls.add(total);
        }

        dto.setMonthlyPayrolls(monthlyPayrolls);
        dto.setMonths(months);

        // Benefits breakdown
        Map<String, Double> benefitMap = new HashMap<>();
        List<Object[]> benefits = benefitRepository.getBenefitBreakdown();
        for (Object[] row : benefits) {
            benefitMap.put((String) row[0], ((Number) row[1]).doubleValue());
        }

        dto.setBenefitBreakdown(benefitMap);

        return dto;
    }
}
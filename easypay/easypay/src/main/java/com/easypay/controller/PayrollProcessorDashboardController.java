package com.easypay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.dto.PayrollProcessorDashboardDTO;
import com.easypay.service.PayrollProcessorDashboardService;

@RestController
@RequestMapping("/api/payroll-processor/dashboard")
@CrossOrigin
public class PayrollProcessorDashboardController {

    @Autowired
    private PayrollProcessorDashboardService dashboardService;

    @GetMapping
    public PayrollProcessorDashboardDTO getDashboardData() {
        return dashboardService.getDashboardStats();
    }
}
package com.easypay.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypay.dto.HrDashboardDTO;
import com.easypay.service.HrDashboardService;

@RestController
@RequestMapping("/api/hr-manager/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class HrDashboardController {

    private HrDashboardService dashboardService;

    public HrDashboardController(HrDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public HrDashboardDTO getSummary() {
        return dashboardService.getDashboardStats();
    }
}
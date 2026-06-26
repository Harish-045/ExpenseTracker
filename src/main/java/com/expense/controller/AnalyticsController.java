package com.expense.controller;

import com.expense.dto.AnalyticsResponse;
import com.expense.dto.DashboardResponse;
import com.expense.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/total")
    public Double totalExpense() {
        return analyticsService.getTotalExpense();
    }

    @GetMapping("/category")
    public List<AnalyticsResponse> categoryWise() {
        return analyticsService.categoryWise();
    }

    @GetMapping("/monthly")
    public List<AnalyticsResponse> monthlyReport() {
        return analyticsService.monthlyReport();
    }

    @GetMapping("/remaining-budget")
    public Double remainingBudget() {
        return analyticsService.remainingBudget();
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(
                analyticsService.getDashboardData()
        );
    }
}
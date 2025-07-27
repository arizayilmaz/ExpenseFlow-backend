package com.expenseflow.api.controller;

import com.expenseflow.api.dto.MonthlySummaryDto;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReportsController {

    private final ReportService reportService;

    @GetMapping("/monthly-summary")
    public ResponseEntity<List<MonthlySummaryDto>> getMonthlySummary(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getMonthlySpendingTrend(user));
    }

    @GetMapping("/category-breakdown")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryBreakdown(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getCategoryBreakdown(user));
    }

    @GetMapping("/investment-summary")
    public ResponseEntity<Map<String, Object>> getInvestmentSummary(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getInvestmentSummary(user));
    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getDashboardStats(user));
    }
}
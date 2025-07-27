package com.expenseflow.api.controller;

import com.expenseflow.api.dto.MonthlySummaryDto;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly-summary")
    public ResponseEntity<List<MonthlySummaryDto>> getMonthlySummary(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getMonthlySpendingTrend(user));
    }
}
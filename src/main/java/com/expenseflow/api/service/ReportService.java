package com.expenseflow.api.service;

import com.expenseflow.api.dto.MonthlySummaryDto;
import com.expenseflow.api.entity.Subscription;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.repository.AssetRepository;
import com.expenseflow.api.repository.ExpenseRepository;
import com.expenseflow.api.repository.InvestmentRepository;
import com.expenseflow.api.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExpenseRepository expenseRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AssetRepository assetRepository;
    private final InvestmentRepository investmentRepository;

    public List<MonthlySummaryDto> getMonthlySpendingTrend(User user) {
        Instant startDate = Instant.now().minus(365, ChronoUnit.DAYS);

        List<MonthlySummaryDto> expenseSummaries = expenseRepository.findMonthlyExpenseSummary(user, startDate);
        Map<String, BigDecimal> monthlyExpenseMap = expenseSummaries.stream()
                .collect(Collectors.toMap(MonthlySummaryDto::getMonth, MonthlySummaryDto::getTotalSpending));

        BigDecimal totalSubscriptionCost = subscriptionRepository.findByUser(user).stream()
                .map(Subscription::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        monthlyExpenseMap.forEach((month, total) ->
                monthlyExpenseMap.put(month, total.add(totalSubscriptionCost))
        );

        return monthlyExpenseMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MonthlySummaryDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Map<String, BigDecimal> getCategoryBreakdown(User user) {
        Instant startDate = Instant.now().minus(30, ChronoUnit.DAYS);
        return expenseRepository.findCategoryBreakdown(user, startDate);
    }

    public Map<String, Object> getInvestmentSummary(User user) {
        Map<String, Object> summary = new HashMap<>();
        
        BigDecimal totalInvestmentValue = investmentRepository.findByUser(user).stream()
                .map(investment -> investment.getInitialValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long investmentCount = investmentRepository.countByUser(user);
        
        Map<String, Long> investmentsByType = investmentRepository.findByUser(user).stream()
                .collect(Collectors.groupingBy(
                    investment -> investment.getType().name(),
                    Collectors.counting()
                ));

        summary.put("totalValue", totalInvestmentValue);
        summary.put("totalCount", investmentCount);
        summary.put("byType", investmentsByType);
        
        return summary;
    }

    public Map<String, Object> getDashboardStats(User user) {
        Map<String, Object> stats = new HashMap<>();
        
        BigDecimal totalAssets = assetRepository.findByUser(user).stream()
                .map(asset -> asset.getCurrentValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyExpenses = expenseRepository.findMonthlyTotal(user, 
            Instant.now().minus(30, ChronoUnit.DAYS));

        BigDecimal monthlySubscriptions = subscriptionRepository.findByUser(user).stream()
                .map(Subscription::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalInvestments = investmentRepository.findByUser(user).stream()
                .map(investment -> investment.getInitialValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        stats.put("totalAssets", totalAssets != null ? totalAssets : BigDecimal.ZERO);
        stats.put("monthlyExpenses", monthlyExpenses != null ? monthlyExpenses : BigDecimal.ZERO);
        stats.put("monthlySubscriptions", monthlySubscriptions != null ? monthlySubscriptions : BigDecimal.ZERO);
        stats.put("totalInvestments", totalInvestments != null ? totalInvestments : BigDecimal.ZERO);
        
        return stats;
    }
}
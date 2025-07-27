package com.expenseflow.api.repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.expenseflow.api.dto.MonthlySummaryDto;
import com.expenseflow.api.entity.Expense;
import com.expenseflow.api.entity.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUser(User user);

    Optional<Expense> findByIdAndUser(UUID id, User user);

    boolean existsByIdAndUser(UUID id, User user);

    @Query("SELECT FUNCTION('TO_CHAR', e.date, 'YYYY-MM') AS month, SUM(e.amount) AS totalSpending " +
            "FROM Expense e WHERE e.user = :user AND e.date >= :startDate " +
            "GROUP BY FUNCTION('TO_CHAR', e.date, 'YYYY-MM') " +
            "ORDER BY month ASC")
    List<MonthlySummaryDto> findMonthlyExpenseSummary(@Param("user") User user, @Param("startDate") Instant startDate);

    @Query("SELECT e.category, SUM(e.amount) " +
            "FROM Expense e WHERE e.user = :user AND e.date >= :startDate " +
            "GROUP BY e.category")
    Map<String, BigDecimal> findCategoryBreakdown(@Param("user") User user, @Param("startDate") Instant startDate);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " +
            "FROM Expense e WHERE e.user = :user AND e.date >= :startDate")
    BigDecimal findMonthlyTotal(@Param("user") User user, @Param("startDate") Instant startDate);
}

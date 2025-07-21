package com.expenseflow.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class ExpenseDto {
    private UUID id;
    private String description;
    private BigDecimal amount;
    private String category;
    private Instant date;
}
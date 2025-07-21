package com.expenseflow.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private String category;
}
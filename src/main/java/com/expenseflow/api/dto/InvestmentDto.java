package com.expenseflow.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.expenseflow.api.enums.InvestmentType;

import lombok.Data;

@Data
public class InvestmentDto {
    private UUID id;
    private InvestmentType type;
    private String name;
    private BigDecimal amount;
    private BigDecimal initialValue;
    private Instant purchaseDate;
    private String apiId;
}
package com.expenseflow.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class InvestmentDto {
    private UUID id;
    private String type;
    private String name;
    private BigDecimal amount;
    private BigDecimal initialValue;
    private Instant purchaseDate;
    private String apiId;
}
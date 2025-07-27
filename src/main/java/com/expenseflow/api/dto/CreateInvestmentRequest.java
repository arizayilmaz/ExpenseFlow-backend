package com.expenseflow.api.dto;

import java.math.BigDecimal;

import com.expenseflow.api.enums.InvestmentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateInvestmentRequest {

    @NotNull(message = "Type cannot be null")
    private InvestmentType type;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;

    @NotNull(message = "Purchase price cannot be null")
    @Positive(message = "Purchase price must be a positive number")
    private BigDecimal purchasePrice;

    private String apiId;
}
package com.expenseflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateInvestmentRequest {

    @NotBlank(message = "Investment name cannot be empty")
    private String name;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;

    @NotNull(message = "Purchase price cannot be null")
    @Positive(message = "Purchase price must be a positive number")
    private BigDecimal purchasePrice;

    // apiId boş olabilir, bu yüzden doğrulama eklemiyoruz.
    private String apiId;
}
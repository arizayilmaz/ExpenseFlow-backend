package com.expenseflow.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateAssetRequest {
    @NotBlank(message = "Asset name cannot be empty")
    private String name;

    @NotNull(message = "Current value cannot be null")
    @PositiveOrZero(message = "Current value must be zero or positive")
    private BigDecimal currentValue;
    
    private String iban;
}
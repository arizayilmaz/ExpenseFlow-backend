package com.expenseflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAssetRequest {
    @NotBlank(message = "Asset name cannot be empty")
    private String name;

    @NotBlank(message = "Asset type cannot be empty")
    private String type;

    @NotNull(message = "Current value cannot be null")
    @PositiveOrZero(message = "Current value must be zero or positive")
    private BigDecimal currentValue;

    private String iban;
}
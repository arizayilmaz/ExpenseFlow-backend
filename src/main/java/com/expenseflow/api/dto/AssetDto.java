package com.expenseflow.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AssetDto {
    private UUID id;
    private String name;
    private String type;
    private BigDecimal currentValue;
    private String iban;
}
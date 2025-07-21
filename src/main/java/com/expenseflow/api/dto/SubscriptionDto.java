package com.expenseflow.api.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Data Transfer Object for sending subscription data to the client.
 */
@Data
public class SubscriptionDto {
    private UUID id;
    private String name;
    private BigDecimal amount;
    private int paymentDay;
    private String category;
    private String lastPaidCycle;
}
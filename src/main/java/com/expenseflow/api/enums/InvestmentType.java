package com.expenseflow.api.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InvestmentType {
    COIN("coin"),
    GOLD("gold"), 
    SILVER("silver"),
    STOCK("stock"),
    BOND("bond"),
    MUTUAL_FUND("mutual_fund"),
    ETF("etf"),
    REAL_ESTATE("real_estate"),
    OTHER("other");

    private final String value;

    InvestmentType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static InvestmentType fromValue(String value) {
        for (InvestmentType type : InvestmentType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown investment type: " + value);
    }
} 
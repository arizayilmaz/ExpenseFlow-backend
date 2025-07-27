package com.expenseflow.api.dto;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private String error;
    private Instant timestamp;
    private String path;
    private List<String> details;
} 
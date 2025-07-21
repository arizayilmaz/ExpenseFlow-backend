package com.expenseflow.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents the 'subscriptions' table in the database.
 * Each instance of this class is a row in that table.
 */
@Entity
@Table(name = "subscriptions")
@Data // Lombok: Automatically generates getters, setters, toString(), etc.
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Automatically generates a unique ID
    private UUID id;

    @Column(nullable = false)
    private String name;

    // Using BigDecimal for financial values is a best practice to avoid floating-point errors.
    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "payment_day", nullable = false)
    private int paymentDay;

    @Column(nullable = false)
    private String category;

    @Column(name = "last_paid_cycle")
    private String lastPaidCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // To prevent infinite loops when serializing to JSON
    private User user;
}
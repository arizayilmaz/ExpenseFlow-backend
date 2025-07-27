package com.expenseflow.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseflow.api.dto.CreateSubscriptionRequest;
import com.expenseflow.api.dto.SubscriptionDto;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.SubscriptionMapper;
import com.expenseflow.api.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptionsForUser(@AuthenticationPrincipal User user) {
        // @AuthenticationPrincipal annotasyonu, Spring Security'nin o anki kullanıcıyı metoda enjekte etmesini sağlar.
        return ResponseEntity.ok(subscriptionService.getAllSubscriptionsForUser(user));
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody CreateSubscriptionRequest request, @AuthenticationPrincipal User user) {
        SubscriptionDto createdSubscription = subscriptionService.createSubscriptionForUser(request, user);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        subscriptionService.deleteSubscriptionForUser(id, user);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(
            @PathVariable UUID id,
            @RequestBody SubscriptionDto subscriptionDto,
            @AuthenticationPrincipal User user) {

        SubscriptionDto updatedSubscription = subscriptionService.updateSubscriptionForUser(id, subscriptionDto, user);
        return ResponseEntity.ok(updatedSubscription);
    }
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<SubscriptionDto> toggleSubscriptionStatus(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        SubscriptionDto updatedSubscription = subscriptionService.toggleSubscriptionStatusForUser(id, user);
        return ResponseEntity.ok(updatedSubscription);
    }

}
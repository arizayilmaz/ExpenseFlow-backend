package com.expenseflow.api.controller;

import com.expenseflow.api.dto.CreateSubscriptionRequest;
import com.expenseflow.api.dto.SubscriptionDto;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.SubscriptionService;
import com.expenseflow.api.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
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
package com.expenseflow.api.service;

import com.expenseflow.api.dto.CreateSubscriptionRequest;
import com.expenseflow.api.dto.SubscriptionDto;
import com.expenseflow.api.entity.Subscription;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.SubscriptionMapper;
import com.expenseflow.api.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Service class containing business logic for subscriptions.
 * All methods are user-aware to ensure data privacy and security.
 */
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    /**
     * Retrieves all subscriptions for the currently authenticated user.
     * @param user The currently logged-in user.
     * @return A list of subscription DTOs.
     */
    @Transactional(readOnly = true)
    public List<SubscriptionDto> getAllSubscriptionsForUser(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findByUser(user);
        return subscriptionMapper.toDtoList(subscriptions);
    }

    /**
     * Creates a new subscription and associates it with the currently authenticated user.
     * @param request DTO containing the new subscription's data.
     * @param user The currently logged-in user.
     * @return The created subscription as a DTO.
     */
    @Transactional
    public SubscriptionDto createSubscriptionForUser(CreateSubscriptionRequest request, User user) {
        Subscription newSubscription = subscriptionMapper.toEntity(request);
        newSubscription.setUser(user); // Associate the subscription with the user
        Subscription savedSubscription = subscriptionRepository.save(newSubscription);
        return subscriptionMapper.toDto(savedSubscription);
    }

    /**
     * Updates an existing subscription for a user.
     * It first verifies that the subscription belongs to the user.
     * @param id The ID of the subscription to update.
     * @param dto DTO with the updated data.
     * @param user The currently logged-in user.
     * @return The updated subscription as a DTO.
     */
    @Transactional
    public SubscriptionDto updateSubscriptionForUser(UUID id, SubscriptionDto dto, User user) {
        // Security Check: Find the subscription by ID and User to ensure ownership.
        Subscription existingSubscription = subscriptionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found or you don't have permission to edit it."));

        // Update fields from the DTO
        existingSubscription.setName(dto.getName());
        existingSubscription.setAmount(dto.getAmount());
        existingSubscription.setPaymentDay(dto.getPaymentDay());
        existingSubscription.setCategory(dto.getCategory());

        Subscription updatedSubscription = subscriptionRepository.save(existingSubscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    /**
     * Deletes a subscription for a user.
     * It first verifies that the subscription belongs to the user.
     * @param id The ID of the subscription to delete.
     * @param user The currently logged-in user.
     */
    @Transactional
    public void deleteSubscriptionForUser(UUID id, User user) {
        // Security Check: Ensure the subscription exists and belongs to the user before deleting.
        if (!subscriptionRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Subscription not found or you don't have permission to delete it.");
        }
        subscriptionRepository.deleteById(id);
    }

    /**
     * Toggles the paid status for the current month for a user's subscription.
     * @param id The ID of the subscription to toggle.
     * @param user The currently logged-in user.
     * @return The updated subscription as a DTO.
     */
    @Transactional
    public SubscriptionDto toggleSubscriptionStatusForUser(UUID id, User user) {
        // DÜZELTME: Gereksiz (Subscription) cast'ı kaldırıldı.
        Subscription subscription = subscriptionRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found or you don't have permission to edit it."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String currentCycle = LocalDate.now().format(formatter);

        if (currentCycle.equals(subscription.getLastPaidCycle())) {
            // If already paid for this cycle, mark as unpaid
            subscription.setLastPaidCycle(null);
        } else {
            // If not paid, mark as paid for this cycle
            subscription.setLastPaidCycle(currentCycle);
        }

        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }
}
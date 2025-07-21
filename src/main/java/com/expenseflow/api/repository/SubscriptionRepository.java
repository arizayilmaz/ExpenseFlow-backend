package com.expenseflow.api.repository;

import com.expenseflow.api.entity.Subscription;
import com.expenseflow.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Subscription entities.
 * Spring Data JPA will automatically implement basic CRUD operations.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findByUser(User user);
    Optional<Subscription> findByIdAndUser(UUID id, User user);
    boolean existsByIdAndUser(UUID id, User user);
}
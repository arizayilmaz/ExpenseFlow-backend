package com.expenseflow.api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expenseflow.api.entity.Investment;
import com.expenseflow.api.entity.User;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    List<Investment> findByUser(User user);
    Optional<Investment> findByIdAndUser(UUID id, User user);
    boolean existsByIdAndUser(UUID id, User user);
    Long countByUser(User user);
}
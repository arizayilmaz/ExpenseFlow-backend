package com.expenseflow.api.repository;

import com.expenseflow.api.entity.Investment;
import com.expenseflow.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, UUID> {
    List<Investment> findByUser(User user);
    Optional<Investment> findByIdAndUser(UUID id, User user);
    boolean existsByIdAndUser(UUID id, User user);
}
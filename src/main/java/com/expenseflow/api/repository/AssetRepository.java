package com.expenseflow.api.repository;

import com.expenseflow.api.entity.Asset;
import com.expenseflow.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findByUser(User user);
    Optional<Asset> findByIdAndUser(UUID id, User user);
    boolean existsByIdAndUser(UUID id, User user);
}
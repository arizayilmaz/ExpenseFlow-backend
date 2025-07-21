package com.expenseflow.api.repository;

import com.expenseflow.api.entity.Expense;
import com.expenseflow.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Expense entities.
 * Extends JpaRepository to get basic CRUD operations for free.
 * Includes custom methods for user-specific data retrieval.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    /**
     * Finds all expenses belonging to a specific user.
     * Spring Data JPA automatically creates the query from the method name.
     * @param user The user entity to search for.
     * @return A list of expenses for the given user.
     */
    List<Expense> findByUser(User user);

    /**
     * Finds a single expense by its ID and ensures it belongs to the specified user.
     * This is crucial for security in update operations.
     * @param id The ID of the expense.
     * @param user The user entity.
     * @return An Optional containing the expense if found and owned by the user.
     */
    Optional<Expense> findByIdAndUser(UUID id, User user);

    /**
     * Checks if an expense with a given ID exists and belongs to the specified user.
     * More efficient than findByIdAndUser when you only need to check for existence.
     * @param id The ID of the expense.
     * @param user The user entity.
     * @return true if the expense exists and is owned by the user, false otherwise.
     */
    boolean existsByIdAndUser(UUID id, User user);
}
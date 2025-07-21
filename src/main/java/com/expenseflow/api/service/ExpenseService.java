package com.expenseflow.api.service;

import com.expenseflow.api.dto.CreateExpenseRequest;
import com.expenseflow.api.dto.ExpenseDto;
import com.expenseflow.api.entity.Expense;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.ExpenseMapper;
import com.expenseflow.api.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class containing business logic for expenses.
 * All methods are user-aware to ensure data privacy and security.
 */
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    /**
     * Retrieves all expenses for the currently authenticated user.
     * @param user The currently logged-in user.
     * @return A list of expense DTOs.
     */
    @Transactional(readOnly = true)
    public List<ExpenseDto> getAllExpensesForUser(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        return expenseMapper.toDtoList(expenses);
    }

    /**
     * Creates a new expense and associates it with the currently authenticated user.
     * @param request DTO containing the new expense's data.
     * @param user The currently logged-in user.
     * @return The created expense as a DTO.
     */
    @Transactional
    public ExpenseDto createExpenseForUser(CreateExpenseRequest request, User user) {
        Expense newExpense = expenseMapper.toEntity(request);
        newExpense.setUser(user); // Associate the expense with the user
        Expense savedExpense = expenseRepository.save(newExpense);
        return expenseMapper.toDto(savedExpense);
    }

    /**
     * Updates an existing expense for a user.
     * It first verifies that the expense belongs to the user.
     * @param id The ID of the expense to update.
     * @param dto DTO with the updated data.
     * @param user The currently logged-in user.
     * @return The updated expense as a DTO.
     */
    @Transactional
    public ExpenseDto updateExpenseForUser(UUID id, ExpenseDto dto, User user) {
        // Security Check: Find the expense by ID and User to ensure ownership.
        Expense existingExpense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found or you don't have permission."));

        // Update fields from the DTO
        existingExpense.setDescription(dto.getDescription());
        existingExpense.setAmount(dto.getAmount());
        existingExpense.setCategory(dto.getCategory());
        existingExpense.setDate(dto.getDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return expenseMapper.toDto(updatedExpense);
    }

    /**
     * Deletes an expense for a user.
     * It first verifies that the expense belongs to the user.
     * @param id The ID of the expense to delete.
     * @param user The currently logged-in user.
     */
    @Transactional
    public void deleteExpenseForUser(UUID id, User user) {
        // Security Check: Ensure the expense exists and belongs to the user before deleting.
        if (!expenseRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Expense not found or you don't have permission to delete it.");
        }
        expenseRepository.deleteById(id);
    }
}
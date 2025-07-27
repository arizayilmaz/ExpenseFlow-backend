package com.expenseflow.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenseflow.api.dto.CreateExpenseRequest;
import com.expenseflow.api.dto.ExpenseDto;
import com.expenseflow.api.entity.Expense;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.ExpenseMapper;
import com.expenseflow.api.repository.ExpenseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional(readOnly = true)
    public List<ExpenseDto> getAllExpensesForUser(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        return expenseMapper.toDtoList(expenses);
    }

    @Transactional
    public ExpenseDto createExpenseForUser(CreateExpenseRequest request, User user) {
        Expense newExpense = expenseMapper.toEntity(request);
        newExpense.setUser(user);
        Expense savedExpense = expenseRepository.save(newExpense);
        return expenseMapper.toDto(savedExpense);
    }

    @Transactional
    public ExpenseDto updateExpenseForUser(UUID id, ExpenseDto dto, User user) {
        Expense existingExpense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found or you don't have permission."));

        existingExpense.setDescription(dto.getDescription());
        existingExpense.setAmount(dto.getAmount());
        existingExpense.setCategory(dto.getCategory());
        existingExpense.setDate(dto.getDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return expenseMapper.toDto(updatedExpense);
    }

    @Transactional
    public void deleteExpenseForUser(UUID id, User user) {
        if (!expenseRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Expense not found or you don't have permission to delete it.");
        }
        expenseRepository.deleteById(id);
    }
}
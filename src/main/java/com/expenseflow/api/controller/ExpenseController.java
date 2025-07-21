package com.expenseflow.api.controller;

import com.expenseflow.api.dto.CreateExpenseRequest;
import com.expenseflow.api.dto.ExpenseDto;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.ExpenseService;
import jakarta.validation.Valid; // @Valid için import
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
// Diğer controller'lar ile tutarlı olması için CrossOrigin eklendi.
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    // DÜZELTME: Metot artık giriş yapmış kullanıcıyı parametre olarak alıyor.
    public ResponseEntity<List<ExpenseDto>> getAllExpensesForUser(@AuthenticationPrincipal User user) {
        // DÜZELTME: Sadece o kullanıcıya ait harcamaları getiren servis metodu çağrılıyor.
        return ResponseEntity.ok(expenseService.getAllExpensesForUser(user));
    }

    @PostMapping
    // DÜZELTME: Metot, işlemi yapan kullanıcıyı alıyor ve gelen veriyi @Valid ile doğruluyor.
    public ResponseEntity<ExpenseDto> createExpenseForUser(@Valid @RequestBody CreateExpenseRequest request, @AuthenticationPrincipal User user) {
        // DÜZELTME: Harcamayı o kullanıcı için oluşturan servis metodu çağrılıyor.
        ExpenseDto createdExpense = expenseService.createExpenseForUser(request, user);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    // DÜZELTME: Metot, işlemi yapan kullanıcıyı güvenlik kontrolü için alıyor.
    public ResponseEntity<Void> deleteExpenseForUser(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        // DÜZELTME: Harcamayı o kullanıcı için silen servis metodu çağrılıyor.
        expenseService.deleteExpenseForUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpenseForUser(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseDto expenseDto, // @Valid buraya da eklendi.
            @AuthenticationPrincipal User user) {

        ExpenseDto updatedExpense = expenseService.updateExpenseForUser(id, expenseDto, user);
        return ResponseEntity.ok(updatedExpense);
    }
}
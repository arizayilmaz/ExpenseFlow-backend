package com.expenseflow.api.controller;

import com.expenseflow.api.dto.CreateInvestmentRequest;
import com.expenseflow.api.dto.InvestmentDto;
import com.expenseflow.api.dto.UpdateInvestmentRequest; // Bu import'un eklendiğinden emin ol
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/investments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping
    public ResponseEntity<List<InvestmentDto>> getAllInvestmentsForUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(investmentService.getAllInvestmentsForUser(user));
    }

    @PostMapping
    public ResponseEntity<InvestmentDto> createInvestmentForUser(
            @Valid @RequestBody CreateInvestmentRequest request,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(investmentService.createInvestmentForUser(request, user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestmentForUser(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        investmentService.deleteInvestmentForUser(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDto> updateInvestment(
            @PathVariable UUID id,
            // DÜZELTME: @Valid annotasyonu eklendi
            @Valid @RequestBody UpdateInvestmentRequest request,
            @AuthenticationPrincipal User user) {

        InvestmentDto updatedInvestment = investmentService.updateInvestmentForUser(id, request, user);
        return ResponseEntity.ok(updatedInvestment);
    }
}
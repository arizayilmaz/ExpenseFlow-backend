package com.expenseflow.api.service;

import com.expenseflow.api.dto.CreateInvestmentRequest;
import com.expenseflow.api.dto.InvestmentDto;
import com.expenseflow.api.dto.UpdateInvestmentRequest;
import com.expenseflow.api.entity.Investment;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.InvestmentMapper;
import com.expenseflow.api.repository.InvestmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final InvestmentMapper investmentMapper;

    @Transactional(readOnly = true)
    public List<InvestmentDto> getAllInvestmentsForUser(User user) {
        return investmentMapper.toDtoList(investmentRepository.findByUser(user));
    }

    @Transactional
    public InvestmentDto createInvestmentForUser(CreateInvestmentRequest request, User user) {
        Investment newInvestment = investmentMapper.toEntity(request);
        newInvestment.setUser(user);

        // DÜZELTME: Eksik olan hesaplama mantığı eklendi.
        // Başlangıç değeri = Miktar * Birim Alış Fiyatı
        BigDecimal initialValue = request.getAmount().multiply(request.getPurchasePrice());
        newInvestment.setInitialValue(initialValue);

        Investment savedInvestment = investmentRepository.save(newInvestment);
        return investmentMapper.toDto(savedInvestment);
    }

    @Transactional
    public InvestmentDto updateInvestmentForUser(UUID id, UpdateInvestmentRequest request, User user) {
        Investment existingInvestment = investmentRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Investment not found or you don't have permission."));

        existingInvestment.setName(request.getName());
        existingInvestment.setAmount(request.getAmount());
        existingInvestment.setApiId(request.getApiId());

        BigDecimal newInitialValue = request.getAmount().multiply(request.getPurchasePrice());
        existingInvestment.setInitialValue(newInitialValue);

        Investment updatedInvestment = investmentRepository.save(existingInvestment);
        return investmentMapper.toDto(updatedInvestment);
    }

    @Transactional
    public void deleteInvestmentForUser(UUID id, User user) {
        if (!investmentRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Investment not found or you don't have permission to delete it.");
        }
        investmentRepository.deleteById(id);
    }
}
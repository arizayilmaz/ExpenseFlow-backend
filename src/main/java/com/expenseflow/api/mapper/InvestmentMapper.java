package com.expenseflow.api.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.expenseflow.api.dto.CreateInvestmentRequest;
import com.expenseflow.api.dto.InvestmentDto;
import com.expenseflow.api.entity.Investment;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {
    InvestmentDto toDto(Investment investment);
    List<InvestmentDto> toDtoList(List<Investment> investments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "initialValue", ignore = true) // Will be calculated in service
    @Mapping(target = "purchaseDate", expression = "java(java.time.Instant.now())")
    Investment toEntity(CreateInvestmentRequest request);
}
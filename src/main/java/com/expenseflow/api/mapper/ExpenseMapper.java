package com.expenseflow.api.mapper;

import com.expenseflow.api.dto.CreateExpenseRequest;
import com.expenseflow.api.dto.ExpenseDto;
import com.expenseflow.api.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDto toDto(Expense expense);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", expression = "java(java.time.Instant.now())")
    Expense toEntity(CreateExpenseRequest request);

    List<ExpenseDto> toDtoList(List<Expense> expenses);
}
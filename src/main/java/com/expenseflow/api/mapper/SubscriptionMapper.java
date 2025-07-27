package com.expenseflow.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.expenseflow.api.dto.CreateSubscriptionRequest;
import com.expenseflow.api.dto.SubscriptionDto;
import com.expenseflow.api.entity.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDto toDto(Subscription subscription);

    List<SubscriptionDto> toDtoList(List<Subscription> subscriptions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lastPaidCycle", ignore = true)
    Subscription toEntity(CreateSubscriptionRequest request);
}
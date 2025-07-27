package com.expenseflow.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.expenseflow.api.dto.AssetDto;
import com.expenseflow.api.dto.CreateAssetRequest;
import com.expenseflow.api.entity.Asset;

@Mapper(componentModel = "spring")
public interface AssetMapper {
    AssetDto toDto(Asset asset);
    List<AssetDto> toDtoList(List<Asset> assets);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Asset toEntity(CreateAssetRequest request);
}
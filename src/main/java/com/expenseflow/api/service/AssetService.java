package com.expenseflow.api.service;

import com.expenseflow.api.dto.AssetDto;
import com.expenseflow.api.dto.CreateAssetRequest;
import com.expenseflow.api.dto.UpdateAssetRequest;
import com.expenseflow.api.entity.Asset;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.mapper.AssetMapper;
import com.expenseflow.api.repository.AssetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Transactional(readOnly = true)
    public List<AssetDto> getAllAssetsForUser(User user) {
        return assetMapper.toDtoList(assetRepository.findByUser(user));
    }

    @Transactional
    public AssetDto createAssetForUser(CreateAssetRequest request, User user) {
        Asset newAsset = assetMapper.toEntity(request);
        newAsset.setUser(user);
        Asset savedAsset = assetRepository.save(newAsset);
        return assetMapper.toDto(savedAsset);
    }

    @Transactional
    public AssetDto updateAssetForUser(UUID id, UpdateAssetRequest request, User user) {
        Asset existingAsset = assetRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or you don't have permission."));

        existingAsset.setName(request.getName());
        existingAsset.setCurrentValue(request.getCurrentValue());

        Asset updatedAsset = assetRepository.save(existingAsset);
        return assetMapper.toDto(updatedAsset);
    }

    @Transactional
    public void deleteAssetForUser(UUID id, User user) {
        if (!assetRepository.existsByIdAndUser(id, user)) {
            throw new EntityNotFoundException("Asset not found or you don't have permission to delete it.");
        }
        assetRepository.deleteById(id);
    }
}
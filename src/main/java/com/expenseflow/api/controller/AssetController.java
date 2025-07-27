package com.expenseflow.api.controller;

import com.expenseflow.api.dto.AssetDto;
import com.expenseflow.api.dto.CreateAssetRequest;
import com.expenseflow.api.dto.UpdateAssetRequest;
import com.expenseflow.api.entity.User;
import com.expenseflow.api.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<AssetDto>> getAllAssetsForUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assetService.getAllAssetsForUser(user));
    }

    @PostMapping
    public ResponseEntity<AssetDto> createAssetForUser(@Valid @RequestBody CreateAssetRequest request, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(assetService.createAssetForUser(request, user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDto> updateAssetForUser(@PathVariable UUID id, @Valid @RequestBody UpdateAssetRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assetService.updateAssetForUser(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetForUser(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        assetService.deleteAssetForUser(id, user);
        return ResponseEntity.noContent().build();
    }
}
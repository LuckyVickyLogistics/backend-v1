package com.luckylogistics.hub.presentation.controller;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.hub.application.dto.*;
import com.luckylogistics.hub.application.service.HubService;
import com.luckylogistics.hub.presentation.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<HubCreateResponse>> createHub(
            @RequestBody HubCreateRequest requestDto,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        HubCreateResponse response = hubService.createHub(requestDto, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // READ - 전체
    @GetMapping
    public ResponseEntity<ApiResponse<List<HubResponse>>> getAllHubs() {
        List<HubResponse> hubs = hubService.getAllHubs();
        return ResponseEntity.ok(ApiResponse.success(hubs));
    }

    // READ - 단건
    @GetMapping("/{hubId}")
    public ResponseEntity<ApiResponse<HubResponse>> getHub(
            @PathVariable UUID hubId
    ) {
        HubResponse hub = hubService.getHub(hubId);
        return ResponseEntity.ok(ApiResponse.success(hub));
    }

    // UPDATE
    @PutMapping("/{hubId}")
    public ResponseEntity<ApiResponse<HubResponse>> updateHub(
            @PathVariable UUID hubId,
            @RequestBody HubUpdateRequest requestDto,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        HubResponse updated = hubService.updateHub(hubId, requestDto, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    // DELETE (soft delete)
    @DeleteMapping("/{hubId}")
    public ResponseEntity<ApiResponse<Void>> deleteHub(
            @PathVariable UUID hubId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        hubService.deleteHub(hubId, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success());
    }
}

package com.luckylogistics.hub.presentation.controller;

import com.luckylogistics.hub.application.dto.*;
import com.luckylogistics.hub.application.service.HubManagerService;
import com.luckylogistics.hub.presentation.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hubs/manager")
@RequiredArgsConstructor
public class HubManagerController {

    private final HubManagerService hubManagerService;

    @PostMapping
    public ResponseEntity<ApiResponse<HubManagerCreateResponse>> createHubManager(
            @RequestBody HubManagerCreateRequest requestDto
    ) {
        Long userId = 1L;
        HubManagerCreateResponse response = hubManagerService.createHubManager(requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HubManagerResponse>>> getHubAllManager() {
        List<HubManagerResponse> hubManagerResponses = hubManagerService.getAllHubManagers();
        return ResponseEntity.ok(ApiResponse.success(hubManagerResponses));
    }

    // hubId로 매니저 조회
    @GetMapping("/{hubId}")
    public ResponseEntity<ApiResponse<HubManagerResponse>> getHubManager(
            @PathVariable UUID hubId
    ) {
        HubManagerResponse hubManagerResponse = hubManagerService.getHubManager(hubId);
        return ResponseEntity.ok(ApiResponse.success(hubManagerResponse));
    }
    // hubId로 매니저 슬랙 조회
    @GetMapping("/{hubId}/slack")
    public ResponseEntity<ApiResponse<String>> getHubManagerSlackEmail(
            @PathVariable UUID hubId
    ) {
        HubManagerResponse hubManagerResponse = hubManagerService.getHubManager(hubId);
        return ResponseEntity.ok(ApiResponse.success(hubManagerResponse.email()));
    }
    // userId로 허브 조회
    @GetMapping("/{userId}/hub")
    public ResponseEntity<ApiResponse<HubResponse>> getHubByUserId(
            @PathVariable Long userId
    ) {
        HubResponse hubResponse = hubManagerService.getHubByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(hubResponse));
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ApiResponse<HubManagerResponse>> updateHubManager(
            @PathVariable UUID managerId,
            @RequestBody HubManagerUpdateRequest requestDto
    ) {
        Long userId = 1L;
        HubManagerResponse hubManagerResponse = hubManagerService.updateHubManager(managerId, requestDto, userId);
        return ResponseEntity.ok(ApiResponse.success(hubManagerResponse));
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<ApiResponse<Void>> deleteHubManager(
            @PathVariable UUID managerId
    ) {
        Long userId = 1L;
        hubManagerService.deleteHubManager(managerId,userId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}

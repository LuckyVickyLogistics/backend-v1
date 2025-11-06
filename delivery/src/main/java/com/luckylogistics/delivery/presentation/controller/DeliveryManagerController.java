package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.DeliveryManagerResponse;
import com.luckylogistics.delivery.application.service.DeliveryManagerService;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerController {

    private final DeliveryManagerService deliveryManagerService;

    /**
     * 배송 담당자 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CreateDeliveryManagerResponse>> createDeliveryManager(
            @Valid @RequestBody CreateDeliveryManagerRequest request,
            @RequestHeader("X-User-Id") Long currentUserId
    ) {
        CreateDeliveryManagerResponse response = deliveryManagerService.createDeliveryManager(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "배송 담당자가 생성되었습니다"));
    }

    /**
     * 배송 담당자 단건 조회
     * - X-User-Id, X-User-Role: 권한 검증 필요
     */
    @GetMapping("/{deliveryManagerId}")
    public ResponseEntity<ApiResponse<DeliveryManagerResponse>> getDeliveryManager(
            @PathVariable Long deliveryManagerId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        DeliveryManagerResponse response = deliveryManagerService.getDeliveryManager(
                deliveryManagerId, currentUserId, currentUserRole
        );
        return ResponseEntity.ok(ApiResponse.success(response, "배송 담당자가 조회되었습니다"));
    }
}
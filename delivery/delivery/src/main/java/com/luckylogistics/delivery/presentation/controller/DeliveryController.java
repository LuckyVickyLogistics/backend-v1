package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.*;
import com.luckylogistics.delivery.application.service.DeliveryService;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 생성
     * Order Service에서 주문 생성 시 자동 호출
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CreateDeliveryResponse>> createDelivery(
            @Valid @RequestBody CreateDeliveryRequest request,
            @RequestHeader("X-User-Id") Long currentUserId
    ) {
        CreateDeliveryResponse response = deliveryService.createDelivery(request, currentUserId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "배송이 생성되었습니다"));
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<ApiResponse<DeliveryResponse>> getDelivery(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        DeliveryResponse response = deliveryService.getDelivery(deliveryId, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송이 조회되었습니다"));
    }

    @PatchMapping("/{deliveryId}/status")
    public ResponseEntity<ApiResponse<UpdateDeliveryResponse>> updateDeliveryStatus(
            @PathVariable UUID deliveryId,
            @Valid @RequestBody UpdateDeliveryStatusRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        UpdateDeliveryResponse response = deliveryService.updateDeliveryStatus(
                deliveryId, request, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송 상태가 변경되었습니다"));
    }
}
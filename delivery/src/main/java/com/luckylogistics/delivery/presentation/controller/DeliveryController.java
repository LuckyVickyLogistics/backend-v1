package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.*;
import com.luckylogistics.delivery.application.service.DeliveryService;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@Tag(name = "배송", description = "배송 관리 API")
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 생성
     * Order Service에서 주문 생성 시 자동 호출
     */
    @Operation(summary = "배송 생성", description = "Order Service에서 주문 생성 시 자동 호출")
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
    @Operation(summary = "배송 단건 조회", description = "특정 배송의 상세 정보를 조회합니다")
    @GetMapping("/{deliveryId}")
    public ResponseEntity<ApiResponse<DeliveryResponse>> getDelivery(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        DeliveryResponse response = deliveryService.getDelivery(deliveryId, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송이 조회되었습니다"));
    }

    @Operation(summary = "배송 상태 변경", description = "배송의 상태를 변경합니다")
    @PutMapping("/{deliveryId}/status")
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

    @Operation(summary = "배송 삭제", description = "배송을 삭제합니다")
    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<ApiResponse<Void>> deleteDelivery(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        deliveryService.deleteDelivery(deliveryId, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success("배송이 삭제되었습니다"));
    }

    @Operation(summary = "배송 목록 조회", description = "배송 목록을 조회합니다 (상태, 출발/도착 허브 필터링 가능)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DeliverySummaryResponse>>> getDeliveries(
            @RequestParam(required = false) DeliveryStatus status,
            @RequestParam(required = false) UUID departureHubId,
            @RequestParam(required = false) UUID arrivalHubId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        Page<DeliverySummaryResponse> response = deliveryService.getDeliveries(
                status, departureHubId, arrivalHubId, page, size, sortBy, direction, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송 목록이 조회되었습니다"));
    }

    @Operation(summary = "배송별 배송 경로 목록 조회", description = "특정 배송의 모든 경로를 조회합니다")
    @GetMapping("/{deliveryId}/routes")
    public ResponseEntity<ApiResponse<List<DeliveryRouteResponse>>> getDeliveryRoutes(
            @PathVariable UUID deliveryId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        List<DeliveryRouteResponse> response = deliveryService.getDeliveryRoutes(
                deliveryId,
                currentUserId,
                currentUserRole
        );

        return ResponseEntity.ok(ApiResponse.success(response, "특정 배송의 모든 경로가 조회되었습니다"));
    }
}
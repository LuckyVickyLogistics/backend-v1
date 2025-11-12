package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.DeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteStatusRequest;
import com.luckylogistics.delivery.application.service.DeliveryRouteService;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-routes")
@RequiredArgsConstructor
public class DeliveryRouteController {

    private final DeliveryRouteService deliveryRouteService;

    @PutMapping("/{routeId}/status")
    public ResponseEntity<ApiResponse<UpdateDeliveryRouteResponse>> updateDeliveryRouteStatus(
            @PathVariable UUID routeId,
            @Valid @RequestBody UpdateDeliveryRouteStatusRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        UpdateDeliveryRouteResponse response = deliveryRouteService.updateDeliveryRouteStatus(
                routeId, request, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송 경로 상태가 변경되었습니다"));
    }

    @GetMapping("/{deliveryRouteId}")
    public ResponseEntity<ApiResponse<DeliveryRouteResponse>> getDeliveryRoute(
            @PathVariable UUID deliveryRouteId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {

        DeliveryRouteResponse response = deliveryRouteService.getDeliveryRoute(
                deliveryRouteId,
                currentUserId,
                currentUserRole
        );

        return ResponseEntity.ok(ApiResponse.success(response, "배송 경로가 조회되었습니다"));
    }
}

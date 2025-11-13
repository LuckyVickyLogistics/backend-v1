package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.DeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteStatusRequest;
import com.luckylogistics.delivery.application.service.DeliveryRouteService;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-routes")
@RequiredArgsConstructor
@Tag(name = "배송 경로", description = "배송 경로 관리 API")
public class DeliveryRouteController {

    private final DeliveryRouteService deliveryRouteService;

    @Operation(summary = "배송 경로 상태 변경", description = "배송 경로의 상태를 변경합니다")
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

    @Operation(summary = "배송 경로 단건 조회", description = "특정 배송 경로의 상세 정보를 조회합니다")
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

    @Operation(
            summary = "배송 담당자별 경로 목록 조회",
            description = """
                    배송 담당자별로 경로를 조회합니다.
                    
                    **deliveryManagerId 파라미터:**
                    - null: 전체 조회 (DELIVERY_MANAGER는 본인 담당 경로 조회만 가능)
                    - 값 있음: 해당 담당자의 경로 조회 (권한별 제한)
                    
                    **권한별 접근 규칙:**
                    - MASTER, COMPANY_MANAGER, HUB_MANAGER: 모든 담당자의 경로 조회 가능
                    - DELIVERY_MANAGER: 본인 경로만 조회 가능 (deliveryManagerId null 또는 본인 ID)
                    """
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DeliveryRouteResponse>>> getDeliveryRoutesByManager(
            @RequestParam(required = false) Long deliveryManagerId,
            @RequestParam(required = false) DeliveryRouteStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        Page<DeliveryRouteResponse> response = deliveryRouteService.getDeliveryRoutesByManager(
                deliveryManagerId, status,
                page, size, sortBy, direction,
                currentUserId, currentUserRole
        );
        return ResponseEntity.ok(ApiResponse.success(response, "배송 경로 목록이 조회되었습니다"));
    }
}

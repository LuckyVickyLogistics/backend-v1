package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.DeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.service.DeliveryManagerService;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-managers")
@RequiredArgsConstructor
@Tag(name = "배송 담당자", description = "배송 담당자 관리 API")
public class DeliveryManagerController {

    private final DeliveryManagerService deliveryManagerService;

    /**
     * 배송 담당자 생성
     */
    @Operation(summary = "배송 담당자 생성", description = "새로운 배송 담당자를 생성합니다")
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
    @Operation(summary = "배송 담당자 단건 조회", description = "특정 배송 담당자의 상세 정보를 조회합니다")
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

    /**
     * 배송 담당자 수정
     * - X-User-Id, X-User-Role: 권한 검증 필요
     */
    @Operation(summary = "배송 담당자 수정", description = "배송 담당자 정보를 수정합니다")
    @PutMapping("/{deliveryManagerId}")
    public ResponseEntity<ApiResponse<DeliveryManagerResponse>> updateDeliveryManager(
            @PathVariable Long deliveryManagerId,
            @Valid @RequestBody UpdateDeliveryManagerRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        DeliveryManagerResponse response = deliveryManagerService.updateDeliveryManager(
                deliveryManagerId, request, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송 담당자가 수정되었습니다"));
    }

    /**
     * 배송 담당자 삭제
     * - X-User-Id, X-User-Role: 권한 검증 필요
     */
    @Operation(summary = "배송 담당자 삭제", description = "배송 담당자를 삭제합니다")
    @DeleteMapping("/{deliveryManagerId}")
    public ResponseEntity<ApiResponse<Void>> deleteDeliveryManager(
            @PathVariable Long deliveryManagerId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        deliveryManagerService.deleteDeliveryManager(deliveryManagerId, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success("배송 담당자가 삭제되었습니다"));
    }

    /**
     * 배송 담당자 목록 조회
     * - X-User-Id, X-User-Role: 권한별 필터링 필요
     */
    @Operation(summary = "배송 담당자 목록 조회", description = "배송 담당자 목록을 조회합니다 (타입, 허브 필터링 가능)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DeliveryManagerResponse>>> getDeliveryManagers(
            @RequestParam(required = false) DeliveryManagerType type,
            @RequestParam(required = false) UUID hubId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        Page<DeliveryManagerResponse> response = deliveryManagerService.getDeliveryManagers(
                type, hubId, page, size, sortBy, direction, currentUserId, currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(response, "배송 담당자 목록이 조회되었습니다"));
    }
}
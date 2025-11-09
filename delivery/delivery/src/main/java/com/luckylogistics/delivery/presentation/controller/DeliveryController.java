package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.CreateDeliveryRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryResponse;
import com.luckylogistics.delivery.application.service.DeliveryService;
import com.luckylogistics.delivery.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
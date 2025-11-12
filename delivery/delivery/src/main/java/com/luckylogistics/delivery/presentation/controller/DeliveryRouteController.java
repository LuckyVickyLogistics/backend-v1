package com.luckylogistics.delivery.presentation.controller;

import com.luckylogistics.delivery.application.dto.DeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteStatusRequest;
import com.luckylogistics.delivery.application.service.DeliveryRouteService;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
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
    public ResponseEntity<UpdateDeliveryRouteResponse> updateDeliveryRouteStatus(
            @PathVariable UUID routeId,
            @Valid @RequestBody UpdateDeliveryRouteStatusRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        UpdateDeliveryRouteResponse response = deliveryRouteService.updateDeliveryRouteStatus(
                routeId, request, currentUserId, currentUserRole);
        return ResponseEntity.ok(response);
    }
}

package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UpdateDeliveryRouteResponse(
        UUID deliveryRouteId,
        UUID deliveryId,
        Integer sequence,
        UUID departureHubId,
        UUID arrivalHubId,
        BigDecimal estimatedDistance,
        Integer estimatedDuration,
        BigDecimal actualDistance,
        Integer actualDuration,
        DeliveryRouteStatus status,
        Long hubDeliveryManagerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UpdateDeliveryRouteResponse from(DeliveryRoute route) {
        return UpdateDeliveryRouteResponse.builder()
                .deliveryRouteId(route.getDeliveryRouteId())
                .deliveryId(route.getDeliveryId())
                .sequence(route.getSequence())
                .departureHubId(route.getDepartureHubId())
                .arrivalHubId(route.getArrivalHubId())
                .estimatedDistance(route.getEstimatedDistance())
                .estimatedDuration(route.getEstimatedDuration())
                .actualDistance(route.getActualDistance())
                .actualDuration(route.getActualDuration())
                .status(route.getStatus())
                .hubDeliveryManagerId(route.getHubDeliveryManager().getDeliveryManagerId())
                .createdAt(route.getCreatedAt())
                .updatedAt(route.getUpdatedAt())
                .build();
    }
}
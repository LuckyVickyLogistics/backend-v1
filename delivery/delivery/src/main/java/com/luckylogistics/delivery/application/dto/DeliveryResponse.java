package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 배송 응답 DTO
 */
@Builder
public record DeliveryResponse(
        UUID deliveryId,
        UUID orderId,
        DeliveryStatus status,
        UUID departureHubId,
        UUID arrivalHubId,
        String deliveryAddress,
        String recipientName,
        String recipientSlackId,
        Long companyDeliveryManagerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<DeliveryRouteResponse> routes
) {
    public static DeliveryResponse from(Delivery delivery, List<DeliveryRoute> routes) {
        return DeliveryResponse.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .departureHubId(delivery.getDepartureHubId())
                .arrivalHubId(delivery.getArrivalHubId())
                .deliveryAddress(delivery.getDeliveryAddress().getDeliveryAddress())
                .recipientName(delivery.getRecipient().getName())
                .recipientSlackId(delivery.getRecipient().getSlackId())
                .companyDeliveryManagerId(delivery.getCompanyDeliveryManager().getDeliveryManagerId())
                .createdAt(delivery.getCreatedAt())
                .updatedAt(delivery.getUpdatedAt())
                .routes(routes.stream()
                        .map(DeliveryRouteResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
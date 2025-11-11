package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * 배송 조회 응답 DTO
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
        LocalTime deliveryManagerStartTime,
        LocalTime deliveryManagerEndTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<DeliveryRouteResponse> routes
) {
    public static DeliveryResponse from(Delivery delivery) {
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
                .deliveryManagerStartTime(delivery.getCompanyDeliveryManager().getStartTime())
                .deliveryManagerEndTime(delivery.getCompanyDeliveryManager().getEndTime())
                .createdAt(delivery.getCreatedAt())
                .updatedAt(delivery.getUpdatedAt())
                .routes(delivery.getRoutes().stream()
                        .map(DeliveryRouteResponse::from)
                        .toList())
                .build();
    }
}
package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 배송 목록 응답 DTO
 */
@Builder
public record DeliverySummaryResponse(
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
        LocalDateTime updatedAt
) {
    public static DeliverySummaryResponse from(Delivery delivery) {
        return DeliverySummaryResponse.builder()
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
                .build();
    }
}
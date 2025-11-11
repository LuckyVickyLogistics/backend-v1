package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * 배송 생성 응답 DTO
 */
@Builder
public record CreateDeliveryResponse(
        UUID deliveryId,
        UUID orderId,
        DeliveryStatus status,
        UUID departureHubId,
        UUID arrivalHubId,
        String deliveryAddress,
        String recipientName,
        String recipientSlackId,
        Long companyDeliveryManagerId,
        LocalTime companyManagerStartTime,
        LocalTime companyManagerEndTime,
        LocalDateTime createdAt,
        List<DeliveryRouteResponse> routes
) {
    public static CreateDeliveryResponse from(Delivery delivery) {
        return CreateDeliveryResponse.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .departureHubId(delivery.getDepartureHubId())
                .arrivalHubId(delivery.getArrivalHubId())
                .deliveryAddress(delivery.getDeliveryAddress().getDeliveryAddress())
                .recipientName(delivery.getRecipient().getName())
                .recipientSlackId(delivery.getRecipient().getSlackId())
                .companyDeliveryManagerId(delivery.getCompanyDeliveryManager().getDeliveryManagerId())
                .companyManagerStartTime(delivery.getCompanyDeliveryManager().getStartTime())
                .companyManagerEndTime(delivery.getCompanyDeliveryManager().getEndTime())
                .createdAt(delivery.getCreatedAt())
                .routes(delivery.getRoutes().stream()
                        .map(DeliveryRouteResponse::from)
                        .toList())
                .build();
    }

    public static CreateDeliveryResponse of(
            Delivery delivery,
            DeliveryManager companyManager,
            List<DeliveryRoute> routes
    ) {
        return CreateDeliveryResponse.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .departureHubId(delivery.getDepartureHubId())
                .arrivalHubId(delivery.getArrivalHubId())
                .deliveryAddress(delivery.getDeliveryAddress().getDeliveryAddress())
                .recipientName(delivery.getRecipient().getName())
                .recipientSlackId(delivery.getRecipient().getSlackId())
                .companyDeliveryManagerId(companyManager.getDeliveryManagerId())
                .companyManagerStartTime(companyManager.getStartTime())
                .companyManagerEndTime(companyManager.getEndTime())
                .createdAt(delivery.getCreatedAt())
                .routes(routes.stream()
                        .map(DeliveryRouteResponse::from)
                        .toList())
                .build();
    }
}
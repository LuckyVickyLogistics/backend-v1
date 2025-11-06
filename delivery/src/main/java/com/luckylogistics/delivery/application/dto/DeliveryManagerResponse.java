package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record DeliveryManagerResponse(
        Long deliveryManagerId,
        UUID hubId,
        String slackId,
        DeliveryManagerType type,
        Integer deliverySequence,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy
) {
    public static DeliveryManagerResponse from(DeliveryManager manager) {
        return DeliveryManagerResponse.builder()
                .deliveryManagerId(manager.getDeliveryManagerId())
                .hubId(manager.getHubId() == null ? null : manager.getHubId().getHubId())
                .slackId(manager.getSlackId().getSlackId())
                .type(manager.getType())
                .deliverySequence(manager.getDeliverySequence())
                .createdAt(manager.getCreatedAt())
                .createdBy(manager.getCreatedBy())
                .updatedAt(manager.getUpdatedAt())
                .updatedBy(manager.getUpdatedBy())
                .build();
    }
}
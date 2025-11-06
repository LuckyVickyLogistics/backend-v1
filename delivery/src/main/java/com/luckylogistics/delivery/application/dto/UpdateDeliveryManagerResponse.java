package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 배송 담당자 수정 응답 DTO
 */
@Builder
public record UpdateDeliveryManagerResponse(
        Long deliveryManagerId,
        UUID hubId,
        String slackId,
        DeliveryManagerType type,
        Integer deliverySequence,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UpdateDeliveryManagerResponse from(DeliveryManager manager) {
        return UpdateDeliveryManagerResponse.builder()
                .deliveryManagerId(manager.getDeliveryManagerId())
                .hubId(manager.getHubId() == null ? null : manager.getHubId().getHubId())
                .slackId(manager.getSlackId().getSlackId())
                .type(manager.getType())
                .deliverySequence(manager.getDeliverySequence())
                .createdAt(manager.getCreatedAt())
                .updatedAt(manager.getUpdatedAt())
                .build();
    }
}
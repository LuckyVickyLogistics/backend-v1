package com.luckylogistics.delivery.infrastructure.client.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubResponse(
        UUID hubId,
        String name,
        String address,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 응답 데이터 유효성 검증
    public void validate() {
        if (hubId == null) {
            throw new IllegalStateException("[HubClient] hubId가 유효하지 않습니다");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("[HubClient] name이 유효하지 않습니다");
        }
    }
}
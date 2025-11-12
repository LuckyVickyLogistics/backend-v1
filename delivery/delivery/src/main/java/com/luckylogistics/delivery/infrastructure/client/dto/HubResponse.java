package com.luckylogistics.delivery.infrastructure.client.dto;

import java.util.UUID;

// TODO: Hub Service 연동
public record HubResponse(
        UUID hubId,
        String name,
        String address,
        Double latitude,
        Double longitude
) {
    public void validate() {
        if (hubId == null) {
            throw new IllegalStateException("[HubClient] hubId가 유효하지 않습니다");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("[HubClient] name이 유효하지 않습니다");
        }
    }
}
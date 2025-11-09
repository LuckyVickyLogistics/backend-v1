package com.luckylogistics.delivery.infrastructure.client.dto;

// TODO: Hub Service 연동
public record HubResponse(
        String hubId,
        String name,
        String address,
        Double latitude,
        Double longitude
) {
}
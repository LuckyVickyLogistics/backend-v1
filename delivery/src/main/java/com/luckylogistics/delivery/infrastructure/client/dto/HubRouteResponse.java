package com.luckylogistics.delivery.infrastructure.client.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record HubRouteResponse(
        Integer sequence,
        UUID departureHubId,
        UUID arrivalHubId,
        BigDecimal distanceKm,
        Integer durationMinutes
) {
    public static HubRouteResponse of(
            int sequence,
            UUID departureHubId,
            UUID arrivalHubId,
            BigDecimal distanceKm,
            int durationMinutes
    ) {
        HubRouteResponse hubRouteResponse = HubRouteResponse.builder()
                .sequence(sequence)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .distanceKm(distanceKm)
                .durationMinutes(durationMinutes)
                .build();
        hubRouteResponse.validate();
        return hubRouteResponse;
    }

    public void validate() {
        if (sequence == null || sequence < 1)
            throw new IllegalArgumentException("[HubClient] 경로 sequence는 1 이상의 값이어야 합니다.");

        if (departureHubId == null)
            throw new IllegalArgumentException("[HubClient] departureHubId가 유효하지 않습니다.");

        if (arrivalHubId == null)
            throw new IllegalArgumentException("[HubClient] arrivalHubId가 유효하지 않습니다");

        if (distanceKm == null || distanceKm.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("[HubClient] distanceKm은 0보다 커야 합니다.");

        if (durationMinutes == null || durationMinutes <= 0)
            throw new IllegalArgumentException("[HubClient] durationMinutes는 0보다 커야 합니다.");
    }
}
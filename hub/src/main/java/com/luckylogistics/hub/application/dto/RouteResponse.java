package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record RouteResponse(
        UUID routeId,
        UUID fromHubId,
        UUID toHubId,
        Double distanceKm,
        Integer duration,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

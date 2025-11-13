package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record RouteCreateResponse(
        UUID routeId,
        UUID fromHubId,
        UUID toHubId,
        Double distanceKm,
        Integer duration
) {
}

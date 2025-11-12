package com.luckylogistics.hub.application.dto;

import java.util.UUID;

public record RoutePlanRequest(
        UUID departureHubId,
        UUID arrivalHubId,
        OptimizeBy optimizeBy // 거리 or 시간
) {
    public enum OptimizeBy { DISTANCE, TIME }
}

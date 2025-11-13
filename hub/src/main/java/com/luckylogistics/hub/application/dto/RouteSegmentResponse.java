package com.luckylogistics.hub.application.dto;

import java.util.UUID;

public record RouteSegmentResponse(
        int sequence,
        UUID departureHubId,
        UUID arrivalHubId,
        double distanceKm,
        int durationMinutes
) {}

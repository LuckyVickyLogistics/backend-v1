package com.luckylogistics.hub.application.dto;

import java.util.List;

public record RoutePlanResponse(
        List<RouteSegmentResponse> routes,
        double totalDistance,
        int totalDuration
) {}

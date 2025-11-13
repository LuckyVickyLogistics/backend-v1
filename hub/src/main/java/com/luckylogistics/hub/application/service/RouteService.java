package com.luckylogistics.hub.application.service;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.hub.application.dto.*;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    RoutePlanResponse plan(RoutePlanRequest request);

    RouteCreateResponse createRoute(RouteCreateRequest request, Long userId, UserRole currentUserRole);

    List<RouteResponse> getAllRoutes();

    RouteResponse getRoute(UUID departureHubId, UUID arrivalHubId);

    RouteResponse updateRoute(UUID routeId, RouteUpdateRequest request, Long userId, UserRole currentUserRole);

    void deleteRoute(UUID routeId, Long userId, UserRole currentUserRole);
}

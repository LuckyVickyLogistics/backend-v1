package com.luckylogistics.hub.application.service;

import com.luckylogistics.hub.application.dto.RoutePlanRequest;
import com.luckylogistics.hub.application.dto.RoutePlanResponse;

public interface RouteService {
    RoutePlanResponse plan(RoutePlanRequest request);
}

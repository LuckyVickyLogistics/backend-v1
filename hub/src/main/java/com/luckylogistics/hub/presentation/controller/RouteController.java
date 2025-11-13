package com.luckylogistics.hub.presentation.controller;

import com.luckylogistics.hub.application.dto.RoutePlanRequest;
import com.luckylogistics.hub.application.dto.RoutePlanResponse;
import com.luckylogistics.hub.application.service.RouteService;
import com.luckylogistics.hub.presentation.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hubs/routes")
@RequiredArgsConstructor
@Tag(name = "Route", description = "허브 간 경로 계획 API")
public class RouteController {

    private final RouteService routeService;

    @Operation(
            summary = "경로 계획",
            description = "출발/도착 허브 사이 최적 경로를 반환합니다. optimizeBy=DISTANCE|TIME (기본: TIME)"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<RoutePlanResponse>> plan(
            @Parameter(description = "출발 허브 ID")
            @RequestParam("from") UUID departureHubId,

            @Parameter(description = "도착 허브 ID")
            @RequestParam("to") UUID arrivalHubId,

            @Parameter(description = "최적화 기준 (DISTANCE 또는 TIME). 기본값은 TIME")
            @RequestParam(value = "optimizeBy", required = false) String optimizeBy
    ) {
        RoutePlanRequest.OptimizeBy criterion;
        if (optimizeBy == null || optimizeBy.isBlank()) {
            criterion = RoutePlanRequest.OptimizeBy.TIME;   // 기본값 TIME
        } else {
            try {
                criterion = RoutePlanRequest.OptimizeBy.valueOf(optimizeBy.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("optimizeBy는 DISTANCE 또는 TIME 이어야 합니다.");
            }
        }

        RoutePlanRequest request = new RoutePlanRequest(departureHubId, arrivalHubId, criterion);
        RoutePlanResponse res = routeService.plan(request);
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}


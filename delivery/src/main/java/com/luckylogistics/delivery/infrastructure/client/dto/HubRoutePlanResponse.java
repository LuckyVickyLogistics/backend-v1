package com.luckylogistics.delivery.infrastructure.client.dto;

import com.luckylogistics.delivery.application.dto.DeliveryRoutePlan;
import com.luckylogistics.delivery.application.dto.DeliveryRouteSegment;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

// TODO: Hub Service 연동
@Builder
public record HubRoutePlanResponse(
        List<HubRouteResponse> routes,
        BigDecimal totalDistance,
        Integer totalDuration
) {
    public HubRoutePlanResponse {
        routes = routes == null ? List.of() : List.copyOf(routes);
    }

    public static HubRoutePlanResponse of(
            List<HubRouteResponse> routes,
            BigDecimal totalDistance,
            Integer totalDuration
    ) {
        HubRoutePlanResponse response = HubRoutePlanResponse.builder()
                .routes(routes)
                .totalDistance(totalDistance)
                .totalDuration(totalDuration)
                .build();

        response.validate();
        return response;
    }

    public void validate() {
        if (routes.isEmpty())
            throw new IllegalArgumentException("[HubClient] routes가 유효하지 않습니다.");

        if (totalDistance == null || totalDistance.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("[HubClient] totalDistance는 0보다 커야 합니다.");

        if (totalDuration == null || totalDuration <= 0)
            throw new IllegalArgumentException("[HubClient] totalDuration은 0보다 커야 합니다.");

        for (HubRouteResponse route : routes)
            route.validate();
    }

    /**
     * application DeliveryRoutePlan 으로 변환
     */
    public DeliveryRoutePlan toDeliveryRoutePlan() {
        List<DeliveryRouteSegment> segments = routes.stream()
                .map(r -> DeliveryRouteSegment.of(
                        r.sequence(),
                        r.departureHubId(),
                        r.arrivalHubId(),
                        r.distanceKm(),
                        r.durationMinutes()
                ))
                .toList();

        return DeliveryRoutePlan.of(segments, totalDistance, totalDuration);
    }
}
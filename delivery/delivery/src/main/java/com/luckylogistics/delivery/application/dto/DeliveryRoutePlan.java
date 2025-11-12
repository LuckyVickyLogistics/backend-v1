package com.luckylogistics.delivery.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 배송 경로 응답 DTO
 */
@Builder
public record DeliveryRoutePlan(
        List<DeliveryRouteSegment> routes,
        BigDecimal totalDistance,
        Integer totalDuration
) {
    public DeliveryRoutePlan {
        routes = routes == null ? List.of() : List.copyOf(routes);
    }

    public static DeliveryRoutePlan of(
            List<DeliveryRouteSegment> routes,
            BigDecimal totalDistance,
            Integer totalDuration
    ) {

        return DeliveryRoutePlan.builder()
                .routes(routes)
                .totalDistance(totalDistance)
                .totalDuration(totalDuration)
                .build();
    }
}
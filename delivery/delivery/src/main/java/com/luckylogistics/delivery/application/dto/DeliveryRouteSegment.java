package com.luckylogistics.delivery.application.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record DeliveryRouteSegment(
        Integer sequence,
        UUID departureHubId,
        UUID arrivalHubId,
        BigDecimal distanceKm,
        Integer durationMinutes
) {
    public static DeliveryRouteSegment of(
            int sequence,
            UUID departureHubId,
            UUID arrivalHubId,
            BigDecimal distanceKm,
            int durationMinutes
    ) {

        return DeliveryRouteSegment.builder()
                .sequence(sequence)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .distanceKm(distanceKm)
                .durationMinutes(durationMinutes)
                .build();
    }
}
package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record RouteUpdateRequest(
        Double distanceKm,
        Integer duration
) {
}

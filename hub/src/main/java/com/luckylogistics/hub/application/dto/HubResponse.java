package com.luckylogistics.hub.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubResponse(
        UUID hubId,
        String name,
        String address,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }

package com.luckylogistics.hub.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubManagerResponse(
        UUID HubManagerId,
        Long userId,
        String name,
        UUID hubId,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}

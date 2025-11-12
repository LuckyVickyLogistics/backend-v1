package com.luckylogistics.hub.application.dto;

import java.util.UUID;

public record HubManagerUpdateRequest(
        Long userId,
        String name,
        UUID hubId,
        String email
){}

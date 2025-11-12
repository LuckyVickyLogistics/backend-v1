package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record HubManagerCreateRequest(
        Long userId,
        String name,
        UUID hubId,
        String email
){}

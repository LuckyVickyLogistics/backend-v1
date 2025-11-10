package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HubCreateRequest(
        String name,
        String address,
        Double latitude,
        Double longitude
){}

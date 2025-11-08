package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record hubCreateCommand(
        String name,
        String address,
        Double latitude,
        Double longitude
){}

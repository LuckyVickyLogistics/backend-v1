package com.luckylogistics.hub.application.dto;

public record HubUpdateRequest(
        String name,
        String address,
        Double latitude,
        Double longitude
) { }

package com.luckylogistics.hub.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record hubCreateResult (
        Instant responseContent
){
    public static hubCreateResult from(Instant responseContent){
        return hubCreateResult.builder()
                .responseContent(responseContent)
                .build();
    }
}

package com.luckylogistics.order.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String code, String message) {
    public static ResponseEntity<ErrorResponse> errorResponse(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode.getCode(), errorCode.getMessage()));
    }
}
// hub-presentation 모듈
package com.luckylogistics.hub.presentation;

import com.luckylogistics.hub.application.exception.HubNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HubNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleHubNotFound(HubNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }
}

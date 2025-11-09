package com.luckylogistics.ai.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luckylogistics.ai.application.exception.AiException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(AiException.class)
	public ResponseEntity<ApiResponse<Void>> handleAiException(AiException ex) {
		log.error("AiException : {}", ex.getMessage(), ex);
		return ResponseEntity.status(ex.getHttpStatus()).body(ApiResponse.error(ex.getMessage()));
	}

	// TODO: 예외 처리 세분화
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception ex) {
		log.error(ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ex.getMessage()));
	}

}

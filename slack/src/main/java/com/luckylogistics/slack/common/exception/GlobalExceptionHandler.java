package com.luckylogistics.slack.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.luckylogistics.slack.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/// 비즈니스 예외
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleAiException(BusinessException ex) {
		log.error("BusinessException : {}", ex.getMessage(), ex);
		return ResponseEntity.status(ex.getErrorCode().getStatus()).body(ApiResponse.error(ex.getErrorCode()));
	}

	/// 입력 값 검증 예외
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldErrors()
			.stream()
			.map(error -> error.getDefaultMessage())
			.findFirst()
			.orElse(ErrorCode.INVALID_INPUT_VALUE.getMessage());

		log.error("MethodArgumentNotValidException : {}", errorMessage, ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, errorMessage)
		);
	}

	/// 도메인 예외
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("DomainException : {}", ex.getMessage(), ex);
		return ResponseEntity.status(ErrorCode.DOMAIN_ERROR.getStatus()).body(
			ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, ex.getMessage())
		);
	}

	/// JSON 파싱 예외
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleJsonParseException(HttpMessageNotReadableException ex) {
		log.warn("HttpMessageNotReadableException: {}", ex.getMessage(), ex);
		return ResponseEntity.status(ErrorCode.BAD_REQUEST.getStatus()).body(
			ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, "잘못된 JSON 형식입니다.")
		);
	}

	/// 나머지 예외
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception ex) {
		log.error("Exception: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
	}

}

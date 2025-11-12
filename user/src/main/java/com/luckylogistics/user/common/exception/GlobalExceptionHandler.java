package com.luckylogistics.user.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.luckylogistics.user.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 비즈니스 예외
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
		ErrorCode error = e.getErrorCode();
		log.warn("[BusinessException] {} - {}", error.getCode(), error.getMessage());
		return ResponseEntity
			.status(error.getHttpStatus())
			.body(ApiResponse.failure(error));
	}

	// Validation 에러
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleNotValidException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		log.warn("[ValidException] {}", message);
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.failure(ErrorCode.INVALID_INPUT_VALUE));
	}

	// 도메인 예외
	@ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<Void>> handleDomainException(RuntimeException e) {
		log.warn("[DomainException] {}", e.getMessage());

		return ResponseEntity
			.status(ErrorCode.DOMAIN_ERROR.getHttpStatus())
			.body(ApiResponse.failure(ErrorCode.DOMAIN_ERROR, e.getMessage()));
	}

	// 그 외 예외
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
		log.error("[UnexpectedError]", e);
		return ResponseEntity
			.internalServerError()
			.body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}

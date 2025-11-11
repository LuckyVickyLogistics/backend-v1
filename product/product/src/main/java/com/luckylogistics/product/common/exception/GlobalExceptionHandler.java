package com.luckylogistics.product.common.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.access.AccessDeniedException; 시큐리티 문제로 추후 작업 예정
//import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //비즈니스 예외 처리

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception){
        ExceptionCode code = exception.getExceptionCode();

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);
        return ErrorResponse.errorResponse(code);
    }

    /**
    // 인증/인가
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException exception) {
        ExceptionCode code = ExceptionCode.FORBIDDEN;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException exception) {
        ExceptionCode code = ExceptionCode.UNAUTHORIZED;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }
**/
    // 입력값 검증
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            BindException.class
    })
    public ResponseEntity<ErrorResponse> handleValidation(Exception exception) {
        ExceptionCode code = ExceptionCode.INVALID_INPUT;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    // Http 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupport(HttpRequestMethodNotSupportedException exception) {
        ExceptionCode code = ExceptionCode.METHOD_NOT_ALLOWED;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParserError(HttpMessageNotReadableException exception) {
        ExceptionCode code = ExceptionCode.INVALID_INPUT;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupport(HttpMediaTypeNotSupportedException exception) {
        ExceptionCode code = ExceptionCode.UNSUPPORTED_MEDIA_TYPE;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    // 데이터 무결성
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        ExceptionCode code = ExceptionCode.DATA_INTEGRITY_VIOLATION;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }

    // 그 외 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ExceptionCode code = ExceptionCode.INTERNAL_SERVER_ERROR;

        log.error("[{}] {}", code.getCode(), code.getMessage(), exception);

        return ErrorResponse.errorResponse(code);
    }
}

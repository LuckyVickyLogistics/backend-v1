package com.luckylogistics.company.common.exception;

import com.luckylogistics.company.common.response.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 규칙 위반 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.error("BusinessException: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode));
    }

    /**
     * 입력 검증 실패 예외 처리 (Spring Validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = (err instanceof FieldError fe) ? fe.getField() : err.getObjectName();
            errors.put(field, err.getDefaultMessage());
        });
        log.warn("Validation failed: {}", errors);

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, errors.toString()));
    }

    /**
     * 도메인 계층 예외 처리
     */
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiResponse<Void>> handleDomainException(RuntimeException e) {
        log.warn("DomainException: {}", e.getMessage());

        return ResponseEntity
                .status(ErrorCode.DOMAIN_ERROR.getStatus())
                .body(ApiResponse.error(ErrorCode.DOMAIN_ERROR, e.getMessage()));
    }

    /**
     * 요청 본문(JSON) 파싱 실패 예외 처리
     * - 잘못된 UUID, Enum, 숫자 형식 등 역직렬화 오류 대응
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleJsonParseException(HttpMessageNotReadableException e) {
        log.warn("JSON parse error: {}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, "형식을 확인해주세요."));
    }

    /**
     * 서버 내부 오류 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Unexpected exception", e);

        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        if ("X-User-Role".equalsIgnoreCase(e.getName())) {
            return ResponseEntity
                    .status(ErrorCode.INVALID_HEADER_USER_ROLE.getStatus())
                    .body(ApiResponse.error(ErrorCode.INVALID_HEADER_USER_ROLE, "value=" + e.getValue()));
        }
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ErrorCode.BAD_REQUEST, e.getMessage()));
    }
}
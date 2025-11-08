package com.luckylogistics.product.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //공통(E)
    INTERNAL_SERVER_ERROR("E01", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT("E02", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("E03", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("E04", "접근이 거부되었습니다.", HttpStatus.FORBIDDEN),
    METHOD_NOT_ALLOWED("E05", "지원하지 않는 API 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    DATA_INTEGRITY_VIOLATION("E06", "데이터 무결성 제약 조건을 위반했습니다.", HttpStatus.CONFLICT),
    UNSUPPORTED_MEDIA_TYPE("E07", "지원하지 않는 요청 형식입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

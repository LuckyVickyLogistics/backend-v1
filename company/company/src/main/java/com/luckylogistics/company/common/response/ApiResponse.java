package com.luckylogistics.company.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luckylogistics.company.common.exception.ErrorCode;

/**
 * 공통 API 응답 래퍼 클래스
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        String code
) {

    /**
     * 성공 응답 생성 (데이터 + 메시지)
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, null);
    }

    /**
     * 실패 응답 생성 (에러 코드 + 메시지)
     */
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(false, errorCode.getMessage(), null, errorCode.getCode());
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(false, errorCode.getMessage() + " (" + message + ")", null, errorCode.getCode());
    }
}
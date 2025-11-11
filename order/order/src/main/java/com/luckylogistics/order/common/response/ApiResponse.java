package com.luckylogistics.order.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luckylogistics.order.common.exception.ExceptionCode;

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

	// 성공 - 데이터 없음
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message, null, null);
	}

    /**
     * 실패 응답 생성 (에러 코드 + 메시지)
     */
    public static <T> ApiResponse<T> error(ExceptionCode exceptionCode) {
        return new ApiResponse<>(false, exceptionCode.getMessage(), null, exceptionCode.getCode());
    }

    public static <T> ApiResponse<T> error(ExceptionCode errorCode, String message) {
        return new ApiResponse<>(false, errorCode.getMessage() + " (" + message + ")", null, errorCode.getCode());
    }
}

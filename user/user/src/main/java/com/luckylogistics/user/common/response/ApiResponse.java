package com.luckylogistics.user.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luckylogistics.user.common.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
	boolean success,
	String message,
	T data,
	String code
) {
	// 성공 응답 - 데이터 있음
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, message, data, null);
	}

	// 성공 응답 - 데이터 없음
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message, null, null);
	}

	// 실패 응답 - 에러 코드
	public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
		return new ApiResponse<>(false, errorCode.getMessage(), null, errorCode.getCode());
	}

	// 실패 응답 - 에러 코드 + 메시지
	public static <T> ApiResponse<T> failure(ErrorCode errorCode, String message) {
		return new ApiResponse<>(false, message, null, errorCode.getCode());
	}
}

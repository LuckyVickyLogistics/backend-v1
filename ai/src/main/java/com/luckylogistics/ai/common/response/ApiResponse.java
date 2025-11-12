package com.luckylogistics.ai.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luckylogistics.ai.common.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

	boolean success,

	String message,

	T data,

	String code

) {

	/// 성공 - 반환 데이터 없음
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message, null, null);
	}

	///  성공 - 반환 데이터 있음
	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data, null);
	}

	/// 실패
	public static <T> ApiResponse<T> error(ErrorCode errorCode) {
		return new ApiResponse<>(false, errorCode.getMessage(), null, errorCode.getCode());
	}

	/// 실패
	public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
		return new ApiResponse<>(false, errorCode.getMessage() + " (" + message + ")", null, errorCode.getCode());
	}

}

package com.luckylogistics.ai.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(

	String messages,

	T data

) {

	public static <T> ApiResponse<T> success() {
		return new ApiResponse<>("success", null);
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>("success", data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(message, null);
	}

}

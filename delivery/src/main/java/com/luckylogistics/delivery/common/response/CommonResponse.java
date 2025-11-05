package com.luckylogistics.delivery.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    private CommonResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 성공
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("200", "요청이 성공했습니다.", data);
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>("200", message, data);
    }

    // 실패
    public static <T> CommonResponse<T> error(ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> CommonResponse<T> error(String code, String message) {
        return new CommonResponse<>(code, message, null);
    }
}
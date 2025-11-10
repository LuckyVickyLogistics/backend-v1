package com.luckylogistics.order.common.exception;

import com.luckylogistics.order.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}

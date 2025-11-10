package com.luckylogistics.product.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionCode exceptionCode;

    public BusinessException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}

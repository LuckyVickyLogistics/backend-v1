package com.luckylogistics.delivery.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DOMAIN_ERROR(HttpStatus.BAD_REQUEST, "D-001", "도메인 규칙을 위반했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "D-002", "잘못된 입력값입니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "D-003", "권한이 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다"),

    // DeliveryManager
    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "DM-001", "배송 담당자를 찾을 수 없습니다"),
    DUPLICATE_DELIVERY_MANAGER(HttpStatus.CONFLICT, "DM-002", "이미 등록된 배송 담당자입니다"),
    INVALID_USER_ROLE(HttpStatus.CONFLICT, "DM-003", "배송 담당자 권한이 없습니다"),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
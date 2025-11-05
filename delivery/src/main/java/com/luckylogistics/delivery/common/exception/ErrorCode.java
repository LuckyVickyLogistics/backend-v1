package com.luckylogistics.delivery.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common (E)
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "E001", "입력값 검증에 실패했습니다."),

    // DeliveryManager (DM)
    INVALID_DELIVERY_MANAGER_TYPE(HttpStatus.BAD_REQUEST, "DM001", "잘못된 배송 담당자 타입입니다."),
    HUB_ID_REQUIRED(HttpStatus.BAD_REQUEST, "DM002", "업체 배송 담당자는 허브 ID가 필요합니다."),
    HUB_ID_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "DM003", "허브 배송 담당자는 허브 ID를 가질 수 없습니다."),
    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "DM004", "배송 담당자를 찾을 수 없습니다."),
    DUPLICATE_DELIVERY_MANAGER(HttpStatus.CONFLICT, "DM005", "이미 배송 담당자로 등록된 사용자입니다."),
    INVALID_USER_ROLE(HttpStatus.CONFLICT, "DM006", "배송 담당자 권한이 없는 사용자입니다."),

    // DeliveryRoute (DR)
    DELIVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "DR001", "배송 경로를 찾을 수 없습니다."),
    INVALID_DELIVERY_ROUTE_REQUEST(HttpStatus.BAD_REQUEST, "DR002", "잘못된 배송 경로 요청입니다."),

    // Delivery (D)
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "D001", "배송 정보를 찾을 수 없습니다."),
    DUPLICATE_DELIVERY(HttpStatus.CONFLICT, "D002", "이미 배송이 생성되었습니다."),

    // System (S)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "서버 오류가 발생했습니다."),
    EXTERNAL_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "S002", "외부 서비스를 사용할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
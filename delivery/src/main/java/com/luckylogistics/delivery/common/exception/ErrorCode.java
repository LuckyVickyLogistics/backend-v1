package com.luckylogistics.delivery.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    DOMAIN_ERROR(HttpStatus.BAD_REQUEST, "D-001", "도메인 규칙을 위반했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "D-002", "잘못된 입력값입니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "D-003", "권한이 없습니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "D-003", "잘못된 요청입니다"),
    INVALID_HEADER_USER_ROLE(HttpStatus.BAD_REQUEST, "D-004", "잘못된 X-User-Role 헤더입니다"),
    FEIGN_ERROR(HttpStatus.BAD_GATEWAY, "D-500", "외부 서비스 요청 중 오류가 발생했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다"),

    // DeliveryManager
    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "DM-001", "배송 담당자를 찾을 수 없습니다"),
    DUPLICATE_DELIVERY_MANAGER(HttpStatus.CONFLICT, "DM-002", "이미 등록된 배송 담당자입니다"),
    INVALID_USER_ROLE(HttpStatus.CONFLICT, "DM-003", "배송 담당자 권한이 없습니다"),
    DELIVERY_MANAGER_SELF_ONLY(HttpStatus.FORBIDDEN, "DM-004","배송 담당자는 본인 정보만 조회할 수 있습니다"),

    // Delivery
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "DD-001", "배송을 찾을 수 없습니다"),
    DELIVERY_DELETED(HttpStatus.BAD_REQUEST, "DD-002", "삭제된 배송입니다"),
    DUPLICATE_DELIVERY(HttpStatus.CONFLICT, "DD-003", "이미 배송이 생성된 주문입니다"),
    FORBIDDEN_DELIVERY_READ(HttpStatus.FORBIDDEN, "DD-004", "해당 배송을 조회할 권한이 없습니다"),
    FORBIDDEN_DELIVERY_MODIFY(HttpStatus.FORBIDDEN, "DD-005", "해당 배송을 수정할 권한이 없습니다"),
    FORBIDDEN_DELIVERY_DELETE(HttpStatus.FORBIDDEN, "DD-006", "해당 배송을 삭제할 권한이 없습니다"),
    FORBIDDEN_NOT_HUB_DELIVERY(HttpStatus.FORBIDDEN, "DD-007", "담당 허브의 배송이 아닙니다"),
    FORBIDDEN_NOT_COMPANY_DELIVERY(HttpStatus.FORBIDDEN, "DD-008", "담당 배송이 아닙니다"),
    FORBIDDEN_DELIVERY_SEARCH(HttpStatus.FORBIDDEN, "DD-009", "해당 배송 목록을 조회할 권한이 없습니다."),

    // DeliveryRoute
    DELIVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "DR-001", "배송 경로를 찾을 수 없습니다"),
    DELIVERY_ROUTE_DELETED(HttpStatus.BAD_REQUEST, "DR-002", "삭제된 배송 경로입니다"),
    FORBIDDEN_ROUTE_READ(HttpStatus.FORBIDDEN, "DR-003", "해당 배송 경로를 조회할 권한이 없습니다"),
    FORBIDDEN_ROUTE_MODIFY(HttpStatus.FORBIDDEN, "DR-004", "해당 배송 경로를 수정할 권한이 없습니다"),
    FORBIDDEN_NOT_HUB_ROUTE(HttpStatus.FORBIDDEN, "DR-005", "담당 허브의 배송 경로가 아닙니다"),
    FORBIDDEN_NOT_ASSIGNED_ROUTE(HttpStatus.FORBIDDEN, "DR-006", "배정된 경로가 아닙니다"),
    FORBIDDEN_ROUTE_SEARCH(HttpStatus.FORBIDDEN, "DR-007", "해당 배송 경로 목록을 조회할 권한이 없습니다."),
    FORBIDDEN_DELIVERY_MANAGER_ROUTE(HttpStatus.FORBIDDEN, "DR-008", "본인이 담당하는 경로만 조회할 수 있습니다"),

    // Hub
    HUB_MANAGER_FORBIDDEN(HttpStatus.FORBIDDEN, "H-001","허브 관리자는 다른 허브의 데이터를 조회할 수 없습니다"),
    USER_HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-002", "허브 관리자의 담당 허브 정보를 찾을 수 없습니다"),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-003", "허브를 찾을 수 없습니다"),
    HUB_SERVICE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "H-004", "허브 서비스 오류가 발생했습니다"),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O-001", "주문을 찾을 수 없습니다"),
    ORDER_SERVICE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "O-002", "주문 서비스 오류가 발생했습니다"),

    // User
    USER_ROLE_UNAUTHORIZED(HttpStatus.FORBIDDEN, "U-001", "해당 권한으로는 접근할 수 없습니다")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
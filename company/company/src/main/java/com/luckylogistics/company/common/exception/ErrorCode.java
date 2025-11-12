package com.luckylogistics.company.common.exception;

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
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다"),

    // Company
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "C-001", "업체를 찾을 수 없습니다"),

    // DeliveryManager
    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "DM-001", "배송 담당자를 찾을 수 없습니다"),
    DUPLICATE_DELIVERY_MANAGER(HttpStatus.CONFLICT, "DM-002", "이미 등록된 배송 담당자입니다"),
    INVALID_USER_ROLE(HttpStatus.CONFLICT, "DM-003", "배송 담당자 권한이 없습니다"),
    DELIVERY_MANAGER_SELF_ONLY(HttpStatus.FORBIDDEN, "DM-004","배송 담당자는 본인 정보만 조회할 수 있습니다"),

    // Hub
    HUB_MANAGER_FORBIDDEN(HttpStatus.FORBIDDEN, "H-001","허브 관리자는 다른 허브의 데이터를 조회할 수 없습니다"),
    USER_HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-002", "허브 관리자의 담당 허브 정보를 찾을 수 없습니다"),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "H-003", "허브를 찾을 수 없습니다"),

    // User
    USER_ROLE_UNAUTHORIZED(HttpStatus.FORBIDDEN, "U-001", "해당 권한으로는 접근할 수 없습니다")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
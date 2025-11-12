package com.luckylogistics.user.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// 공통
	DOMAIN_ERROR(HttpStatus.BAD_REQUEST, "D-001", "도메인 규칙을 위반했습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "D-002", "잘못된 입력값입니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "D-003", "권한이 없습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "D-003", "잘못된 요청입니다."),
	INVALID_HEADER_USER_ROLE(HttpStatus.BAD_REQUEST, "D-004", "잘못된 X-User-Role 헤더입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다."),

	// User
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, " U-001", "사용자를 찾을 수 없습니다."),
	DUPLICATE_USER(HttpStatus.CONFLICT, "U-002", "이미 사용중인 아이디입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U-003", "비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "U-004", "인증이 필요합니다."),
	FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "U-005", "접근 권한이 없습니다."),
	ALREADY_PROCESSED_USER(HttpStatus.CONFLICT, "U-006", "이미 처리된 회원입니다."),
	USER_ROLE_REQUIRED(HttpStatus.BAD_REQUEST, "U-007", "승인 시 role은 필수 입력값입니다."),
	INVALID_USER_STATUS(HttpStatus.BAD_REQUEST, "U-008", "잘못된 회원 상태입니다."),
	HUB_NOT_FOUND(HttpStatus.NOT_FOUND, " U-009", "존재하지 않는 허브입니다."),
	COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, " U-010", "존재하지 않는 업체입니다."),


	// Auth
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "리프레시 토큰이 만료되었습니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "유효하지 않은 리프레시 토큰입니다."),
	LOGGED_OUT_TOKEN(HttpStatus.UNAUTHORIZED, "A-003", "이미 로그아웃된 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}

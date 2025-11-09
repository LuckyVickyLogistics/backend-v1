package com.luckylogistics.ai.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

	// Gemini
	GEMINI_API_BAD_REQUEST(HttpStatus.BAD_REQUEST, "G-001", "Gemini API 요청이 잘못되었습니다."),
	GEMINI_API_FORBIDDEN(HttpStatus.FORBIDDEN, "G-002", "Gemini API 요청에 권한이 없습니다."),
	GEMINI_API_NOT_FOUND(HttpStatus.NOT_FOUND, "G-003", "Gemini API 요청한 리소스를 찾을 수 없습니다."),
	GEMINI_API_TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "G-004", "Gemini API 요청 제한 횟수를 초과했습니다."),
	GEMINI_API_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "G-005", "Gemini API 요청이 타임아웃되었습니다."),
	GEMINI_API_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-006", "Gemini API 서비스 내부에 오류가 발생했습니다."),
	GEMINI_API_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "G-007", "Gemini API 서비스를 일시적으로 호출할 수 없습니다."),
	GEMINI_API_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-008", "Gemini API 서비스에 알 수 없는 오류가 발생했습니다."),
	GEMINI_API_RESPONSE_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G-009", "Gemini API 응답 파싱에 실패했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

}

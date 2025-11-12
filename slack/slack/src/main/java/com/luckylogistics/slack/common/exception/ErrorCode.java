package com.luckylogistics.slack.common.exception;

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
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "D-004", "잘못된 요청입니다."),
	INVALID_HEADER_USER_ROLE(HttpStatus.BAD_REQUEST, "D-005", "잘못된 X-User-Role 헤더입니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "D-006", "리소스를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다."),

	// Slack
	SLACK_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "S-001", "일치하는 Slack 메시지를 찾을 수 없습니다."),
	SLACK_API_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "S-002", "Slack API 토큰이 유효하지 않습니다."),
	SLACK_API_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "S-003", "Slack API 요청이 타임아웃되었습니다."),
	SLACK_API_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-999", "Slack API 서비스 내부에 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

}

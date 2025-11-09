package com.luckylogistics.ai.application.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AiException extends RuntimeException {

	private final HttpStatus httpStatus;

	public AiException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

}

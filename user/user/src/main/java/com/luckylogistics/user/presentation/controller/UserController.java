package com.luckylogistics.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.application.service.UserService;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.presentation.request.UserSignupRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody UserSignupRequest request) {

		SignupCommand command = SignupCommand.builder()
			.username(request.username())
			.password(request.password())
			.slackId(request.slackId())
			.organizationType(request.organizationType())
			.organizationName(request.organizationName())
			.build();

		Long userId = userService.signup(command);

		return ResponseEntity.ok(ApiResponse.success(userId, "회원가입이 요청 되었습니다."));
	}
}

package com.luckylogistics.user.presentation.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.application.service.UserService;
import com.luckylogistics.user.presentation.request.UserSignupRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequest request) {

		SignupCommand command = SignupCommand.builder()
			.username(request.username())
			.password(request.password())
			.slackId(request.slackId())
			.role(request.role())
			.organizationType(request.organizationType())
			.build();

		Long userId = userService.signup(command);

		return ResponseEntity.ok(Map.of("userId", userId, "message", "회원가입 요청 완료"));
	}
}

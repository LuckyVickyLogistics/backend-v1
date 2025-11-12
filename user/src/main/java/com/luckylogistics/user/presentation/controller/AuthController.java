package com.luckylogistics.user.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.user.application.dto.LoginCommand;
import com.luckylogistics.user.application.dto.TokenResponse;
import com.luckylogistics.user.application.service.AuthService;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.infrastructure.jwt.JwtProvider;
import com.luckylogistics.user.presentation.request.UserLoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtProvider jwtProvider;

	// 로그인 (AccessToken + RefreshToken 발급)
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody UserLoginRequest request) {

		LoginCommand command = LoginCommand.builder()
			.username(request.username())
			.password(request.password())
			.build();

		TokenResponse tokenResponse = authService.login(command);

		return ResponseEntity.ok(ApiResponse.success(tokenResponse, "로그인 되었습니다."));
	}

	// AccessToken 재발급
	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissue(HttpServletRequest request) {
		String refreshToken = jwtProvider.resolveToken(request);
		TokenResponse tokenResponse = authService.reissue(refreshToken);
		return ResponseEntity.ok(tokenResponse);
	}

	// 로그아웃 (RefreshToken 삭제 + AccessToken 블랙리스트 등록)
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>>  logout(HttpServletRequest request) {
		String token = jwtProvider.resolveToken(request);
		authService.logout(token);
		return ResponseEntity.ok(ApiResponse.success("로그아웃 되었습니다."));
	}
}

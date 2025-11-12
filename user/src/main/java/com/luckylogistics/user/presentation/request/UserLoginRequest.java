package com.luckylogistics.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
	@NotBlank(message = "아이디는 필수 입력값입니다.")
	String username,
	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	String password
) {}

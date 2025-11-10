package com.luckylogistics.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
	@NotBlank
	String username,
	@NotBlank
	String password
) {}

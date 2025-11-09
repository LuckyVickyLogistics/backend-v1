package com.luckylogistics.user.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserSignupRequest(

	@NotBlank
	@Pattern(regexp = "^[a-z0-9]{4,10}$")
	String username,

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*0-9)(?=.*[!@#$%&*])[a-zA-Z0-9!@#$%&*]{8,15}$")
	String password,

	@Email
	@NotBlank
	String slackId,

	@NotNull
	String role,

	@NotNull
	String organizationType
) {}

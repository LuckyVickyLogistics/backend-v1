package com.luckylogistics.user.presentation.request;

import com.luckylogistics.user.domain.model.UserRole;

import jakarta.validation.constraints.NotBlank;

public record UserStatusUpdateRequest(
	@NotBlank
	String status,
	UserRole role
) {
}

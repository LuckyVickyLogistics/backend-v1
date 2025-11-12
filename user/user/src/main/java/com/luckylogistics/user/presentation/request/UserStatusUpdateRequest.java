package com.luckylogistics.user.presentation.request;

import com.luckylogistics.user.domain.model.Status;
import com.luckylogistics.user.domain.model.UserRole;

import jakarta.validation.constraints.NotBlank;

public record UserStatusUpdateRequest(
	@NotBlank
	Status status,
	UserRole role
) {
}

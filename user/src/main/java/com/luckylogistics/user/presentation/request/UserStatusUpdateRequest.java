package com.luckylogistics.user.presentation.request;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.user.domain.model.Status;

import jakarta.validation.constraints.NotBlank;

public record UserStatusUpdateRequest(
	@NotBlank
	Status status,
	UserRole role
) {
}

package com.luckylogistics.user.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.user.domain.model.OrganizationType;
import com.luckylogistics.user.domain.model.Status;
import com.luckylogistics.user.domain.model.User;

public record UserDetailResponse (
	Long userId,
	UUID identifier,
	String username,
	String slackId,
	UserRole role,
	OrganizationType organizationType,
	UUID organizationId,
	Status status,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
){
	public static UserDetailResponse from(User user) {
		return new UserDetailResponse(
			user.getUserId(),
			user.getIdentifier(),
			user.getUsername(),
			user.getSlackId(),
			user.getRole(),
			user.getOrganizationType(),
			user.getOrganizationId(),
			user.getStatus(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}
}

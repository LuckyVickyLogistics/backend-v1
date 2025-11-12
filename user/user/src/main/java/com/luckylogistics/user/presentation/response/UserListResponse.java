package com.luckylogistics.user.presentation.response;

import java.util.UUID;

import com.luckylogistics.user.domain.model.OrganizationType;
import com.luckylogistics.user.domain.model.Status;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.model.UserRole;

public record UserListResponse (
	Long userId,
	UUID identifier,
	String username,
	String slackId,
	UserRole role,
	Status status,
	OrganizationType organizationType,
	UUID organizationId,
	boolean isDeleted
) {
	public static UserListResponse from(User user) {
		return new UserListResponse(
			user.getUserId(),
			user.getIdentifier(),
			user.getUsername(),
			user.getSlackId(),
			user.getRole(),
			user.getStatus(),
			user.getOrganizationType(),
			user.getOrganizationId(),
			user.isDeleted()
		);
	}
}

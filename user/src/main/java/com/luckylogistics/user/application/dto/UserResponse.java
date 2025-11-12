package com.luckylogistics.user.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.luckylogistics.user.domain.model.OrganizationType;
import com.luckylogistics.user.domain.model.Status;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.model.UserRole;

import lombok.Builder;

@Builder
public record UserResponse(

	UUID identifier, // 외부 식별자
	String username, // 로그인 아이디
	String slackId,
	UserRole role, // MASTER_ADMIN, HUB_MANAGER, DELIVERY_MANAGER, COMPANY_MANAGER
	OrganizationType organizationType, // HUB, COMPANY
	UUID organizationId,
	Status status, // PENDING,APPROVED,REJECTED
	boolean deleted,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static UserResponse from(User user) {
		return UserResponse.builder()
			.identifier(user.getIdentifier())
			.username(user.getUsername())
			.slackId(user.getSlackId())
			.role(user.getRole())
			.organizationType(user.getOrganizationType())
			.organizationId(user.getOrganizationId())
			.status(user.getStatus())
			.deleted(user.isDeleted())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}

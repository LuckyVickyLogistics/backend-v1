package com.luckylogistics.order.infrastructure.client.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.luckylogistics.order.application.dto.UserResponse;

public record GetUserClientResponse(

	Long userId,
	UUID identifier,
	String username,
	String slackId,
	UserRole role,
	OrganizationType organizationType,
	UUID organizationId,
	Status status,
	boolean isDeleted,
	LocalDateTime createdAt,
	LocalDateTime updatedAt

) {

	public static UserResponse of(GetUserClientResponse response) {
		return UserResponse.builder()
			.username(response.username)
			.slackId(response.slackId)
			.companyId(response.organizationId)
			.build();
	}

	public enum UserRole {
		MASTER_ADMIN,
		HUB_MANAGER,
		DELIVERY_MANAGER,
		COMPANY_MANAGER;
	}

	public enum OrganizationType {
		HUB,
		COMPANY;
	}

	public enum Status {
		PENDING,
		APPROVED,
		REJECTED
	}

}

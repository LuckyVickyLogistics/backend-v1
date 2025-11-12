package com.luckylogistics.user.application.dto;

import java.util.UUID;

import com.luckylogistics.user.domain.model.OrganizationType;

import lombok.Builder;

@Builder
public record SignupCommand(
	String username,
	String password,
	String slackId,
	OrganizationType organizationType,
	UUID organizationId,
	String organizationName
) {}

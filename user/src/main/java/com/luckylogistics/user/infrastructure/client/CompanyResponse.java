package com.luckylogistics.user.infrastructure.client;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CompanyResponse(
	UUID companyId,
	String name,
	String address,
	String type,
	UUID hubId
) { }
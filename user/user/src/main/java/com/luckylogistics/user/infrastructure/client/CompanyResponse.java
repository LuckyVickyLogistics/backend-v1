package com.luckylogistics.user.infrastructure.client;

import java.util.UUID;

public record CompanyResponse(
	UUID companyId,
	String name,
	String address,
	String type,
	UUID hubId
) { }
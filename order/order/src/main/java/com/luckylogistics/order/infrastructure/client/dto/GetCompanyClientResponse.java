package com.luckylogistics.order.infrastructure.client.dto;

import java.util.UUID;

import com.luckylogistics.order.application.dto.CompanyHubResponse;

public record GetCompanyClientResponse(

	UUID companyId,
	String name,
	String address,
	CompanyType type,
	UUID hubId

) {

	public enum CompanyType {
		SUPPLIER, CUSTOMER
	}

	public static CompanyHubResponse of(GetCompanyClientResponse response) {
		return CompanyHubResponse.builder().arrivalHubId(response.hubId).build();
	}

}

package com.luckylogistics.user.domain.model;

public enum OrganizationType {
	HUB,
	COMPANY;

	public static OrganizationType from(String type) {
		return OrganizationType.valueOf(type.toUpperCase());
	}
}

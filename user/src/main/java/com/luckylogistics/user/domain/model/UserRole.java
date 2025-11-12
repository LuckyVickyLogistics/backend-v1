package com.luckylogistics.user.domain.model;

public enum UserRole {
	MASTER_ADMIN,
	HUB_MANAGER,
	DELIVERY_MANAGER,
	COMPANY_MANAGER;

	public static UserRole from(String role) {
		return UserRole.valueOf(role.toUpperCase());
	}
}


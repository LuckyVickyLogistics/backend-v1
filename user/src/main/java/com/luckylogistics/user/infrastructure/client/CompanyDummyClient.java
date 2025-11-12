package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.user.common.response.ApiResponse;

@Component
public class CompanyDummyClient {

	public boolean isCompanyExists(UUID companyId) {
		return true; // 실제 서비스 연동 전까지 항상 true
	}

	public ApiResponse<CompanyResponse> getCompany(UUID companyId) {
		CompanyResponse dummy = CompanyResponse.builder()
			.companyId(companyId)
			.name("Dummy Company")
			.address("Dummy Address")
			.type("SUPPLIER")
			.hubId(UUID.randomUUID())
			.build();

		return ApiResponse.success(dummy, "Dummy Company");
	}

	public ApiResponse<List<CompanyResponse>> getCompanies(String name) {
		CompanyResponse dummyA = CompanyResponse.builder()
			.companyId(UUID.randomUUID())
			.name("Dummy Company A")
			.address("Dummy Address A")
			.type("SUPPLIER")
			.hubId(UUID.randomUUID())
			.build();

		CompanyResponse dummyB = CompanyResponse.builder()
			.companyId(UUID.randomUUID())
			.name("Dummy Company B")
			.address("Dummy Address B")
			.type("SUPPLIER")
			.hubId(UUID.randomUUID())
			.build();

		return ApiResponse.success(List.of(dummyA, dummyB), "Dummy Company list");
	}
}

package com.luckylogistics.user.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.user.application.external.CompanyService;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.infrastructure.client.CompanyDummyClient;
import com.luckylogistics.user.infrastructure.client.CompanyResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyServiceAdapter implements CompanyService {

	//private final CompanyFeignClient companyFeignClient;
	private final CompanyDummyClient companyFeignClient;

	@Override
	public List<CompanyResponse> getCompanies(String name) {
		ApiResponse<List<CompanyResponse>> res = companyFeignClient.getCompanies(name);

		if (res == null || !res.success()) {
			throw new RuntimeException(res != null ? res.message() : "getCompanies failed");
		}

		return List.of();
	}

	@Override
	public CompanyResponse getCompany(UUID companyId) {
		ApiResponse<CompanyResponse> res = companyFeignClient.getCompany(companyId);

		if (res == null || !res.success()) {
			throw new RuntimeException(res != null ? res.message() : "getCompany failed");
		}

		return res.data();
	}
}

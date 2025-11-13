package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckylogistics.user.common.response.ApiResponse;

@FeignClient(name = "companies", path = "/api/v1/companies")
public interface CompanyFeignClient {

	@GetMapping
	ApiResponse<List<CompanyResponse>> getCompanies(
		@RequestParam(required = false) String name
	);

	@GetMapping("/{companyId}")
	ApiResponse<CompanyResponse> getCompany(
		@PathVariable(name = "companyId") UUID companyId
	);

}

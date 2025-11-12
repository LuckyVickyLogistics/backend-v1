package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "companies")
public interface CompanyClient {

	@GetMapping("/api/v1/companies")
	ResponseEntity<List<CompanyResponse>> getCompanies(
		@RequestHeader("X-Internal-Request") String internalHeader,
		@RequestParam(required = false) String name
	);

	@GetMapping("/api/v1/{companyId}")
	ResponseEntity<CompanyResponse> getCompany(
		@RequestHeader("X-Internal-Request") String internalHeader,
		@PathVariable(name = "companyId") UUID companyId
	);

}

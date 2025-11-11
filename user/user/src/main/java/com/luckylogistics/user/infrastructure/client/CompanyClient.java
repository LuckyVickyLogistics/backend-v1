package com.luckylogistics.user.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company")
public interface CompanyClient {

	@GetMapping("/api/v1/company/{companyName}")
	CompanyResponse getCompanyByName(@PathVariable("name") String name);

	record CompanyResponse(UUID id, String name) {}
}

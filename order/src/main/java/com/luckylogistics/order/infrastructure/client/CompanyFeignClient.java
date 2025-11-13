package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.dto.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetCompanyClientResponse;

@FeignClient(name = "company")
public interface CompanyFeignClient {
	@GetMapping("/api/v1/companies/{companyId}")
	ApiResponse<GetCompanyClientResponse> getCompany(@PathVariable("companyId") UUID companyId);
    @GetMapping("/api/v1/company-managers/{userId}")
    ApiResponse<CompanyResponse> getCompanyUserId(@PathVariable("userId") Long userId);
}
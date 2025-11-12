package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetCompanyClientResponse;

@FeignClient(name = "company", path = "/api/v1/companies")
public interface CompanyFeignClient {
    @GetMapping("/{companyId}")
    boolean isCompanyIdExists(@PathVariable("companyId") UUID companyId);

	@GetMapping("/companyId")
	ApiResponse<GetCompanyClientResponse> getCompany(@PathVariable("companyId") UUID companyId);
}
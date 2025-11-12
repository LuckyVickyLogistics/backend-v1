package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.product.infrastructure.client.dto.CompanyResponse;
import com.luckylogistics.product.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company")
public interface CompanyFeignClient {
    @GetMapping("/api/v1/companies/{companyId}")
    ApiResponse<CompanyResponse> getCompany(@PathVariable("companyId") UUID companyId);

    @GetMapping("/api/v1/companies/{userId}")
    ApiResponse<CompanyResponse> getCompanyUserId(@PathVariable("userId") Long userId);
}

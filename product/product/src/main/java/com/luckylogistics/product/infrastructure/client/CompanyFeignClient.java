package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.product.infrastructure.client.dto.CompanyResponse;
import com.luckylogistics.product.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company", path ="/api/v1/companies")
public interface CompanyFeignClient {
    @GetMapping("/{companyId}")
    ApiResponse<CompanyResponse> getCompany(@PathVariable("companyId") UUID companyId);

    @GetMapping("/{userId}")
    ApiResponse<CompanyResponse> getCompanyUserId(@PathVariable("userId") Long userId);
}

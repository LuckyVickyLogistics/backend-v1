package com.luckylogistics.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "companyId")
public interface CompanyFeignClient {
    @GetMapping("/{companyId}")
    boolean isCompanyIdExists(@PathVariable("companyId") UUID companyId);
}
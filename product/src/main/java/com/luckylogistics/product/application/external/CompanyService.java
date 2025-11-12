package com.luckylogistics.product.application.external;

import com.luckylogistics.product.infrastructure.client.dto.CompanyResponse;

import java.util.UUID;

public interface CompanyService {
    CompanyResponse getCompany(UUID companyId);
    CompanyResponse getCompanyUserId(Long userId);
}

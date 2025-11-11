package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.CompanyHubResponse;

public interface CompanyService {
    void isCompanyExists(UUID companyId);

	CompanyHubResponse getCompanyHub(UUID companyId);
}

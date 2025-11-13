package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.CompanyHubResponse;
import com.luckylogistics.order.infrastructure.client.dto.CompanyResponse;

public interface CompanyService {
    //void isCompanyExists(UUID companyId);

	CompanyHubResponse getCompanyHub(UUID companyId);
    CompanyResponse getCompanyUserId(Long userId);
}

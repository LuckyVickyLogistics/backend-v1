package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetCompanyClientResponse;

@Component
public class CompanyDummyClient {
    public boolean isCompanyExists(UUID companyId) {
        return true;
    }

	public ApiResponse<GetCompanyClientResponse> getCompany(UUID companyId) {
		GetCompanyClientResponse dummy = new GetCompanyClientResponse(
			companyId,
			"Dummy Company",
			"Dummy Address",
			GetCompanyClientResponse.CompanyType.SUPPLIER,
			UUID.randomUUID()
		);

		return ApiResponse.success(dummy, "Created Dummy Data");
	}
}

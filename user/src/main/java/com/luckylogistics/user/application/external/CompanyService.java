package com.luckylogistics.user.application.external;

import java.util.List;
import java.util.UUID;

import com.luckylogistics.user.infrastructure.client.CompanyResponse;

public interface CompanyService {

	List<CompanyResponse> getCompanies(String name);

	CompanyResponse getCompany(UUID companyId);
}

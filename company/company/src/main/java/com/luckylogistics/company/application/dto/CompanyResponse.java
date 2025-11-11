package com.luckylogistics.company.application.dto;

import com.luckylogistics.company.domain.entity.Company;
import com.luckylogistics.company.domain.entity.CompanyType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyResponse(
    UUID companyId,
    String name,
    String address,
    CompanyType type,
    UUID hubId
) {
    public static CompanyResponse from(Company company) {
        return CompanyResponse.builder()
            .companyId(company.getCompanyId())
            .name(company.getName())
            .address(company.getAddress())
            .type(company.getType())
            .hubId(company.getHubId())
            .build();
    }
}

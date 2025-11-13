package com.luckylogistics.company.application.dto;

import com.luckylogistics.company.domain.entity.CompanyManager;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CompanyManagerResponse(
    UUID managerId,
    Long userId,
    UUID companyId
) {
    public static CompanyManagerResponse from(CompanyManager companyManager) {
        return CompanyManagerResponse.builder()
            .managerId(companyManager.getManagerId())
            .userId(companyManager.getUserId())
            .companyId(companyManager.getCompanyId())
            .build();
    }

}

package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.infrastructure.client.dto.CompanyResponse;
import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.common.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.CompanyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyServiceAdapter implements CompanyService {
    private final CompanyFeignClient companyFeignClient;
    //private final CompanyDummyClient companyFeignClient;


    @Override
    public CompanyResponse getCompany(UUID companyId) {
        ApiResponse<CompanyResponse> response = companyFeignClient.getCompany(companyId);
        return response.data();
    }

    public CompanyResponse getCompanyUserId(Long userId) {
        ApiResponse<CompanyResponse> response = companyFeignClient.getCompanyUserId(userId);
        return response.data();
    }

}

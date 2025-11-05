package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.infrastructure.client.CompanyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyServiceAdapter implements CompanyService {
    private final CompanyFeignClient companyFeignClient;

    @Override
    public void isCompanyExists(UUID companyId) {
        if(!(companyFeignClient.isCompanyIdExists(companyId))){
            throw new RuntimeException("연결된 업체 ID를 찾을 수 없습니다!");
        }
    }
}

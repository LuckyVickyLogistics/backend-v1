package com.luckylogistics.order.infrastructure.exteranal;

import com.luckylogistics.order.application.external.CompanyService;
import com.luckylogistics.order.infrastructure.client.CompanyDummyClient;
import com.luckylogistics.order.infrastructure.client.CompanyFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyServiceAdapter implements CompanyService {
  //  private final CompanyFeignClient  companyFeignClient;
    private final CompanyDummyClient companyFeignClient;


    @Override
    public void isCompanyExists(UUID companyId) {
        if(! (companyFeignClient.isCompanyExists(companyId))){
            throw new RuntimeException("업체 연결정보가 존재하지 않습니다.");
        }

    }
}

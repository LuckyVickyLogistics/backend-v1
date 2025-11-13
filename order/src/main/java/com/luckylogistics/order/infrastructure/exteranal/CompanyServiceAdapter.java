package com.luckylogistics.order.infrastructure.exteranal;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.CompanyFeignClient;
import com.luckylogistics.order.infrastructure.client.dto.CompanyResponse;
import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.CompanyHubResponse;
import com.luckylogistics.order.application.external.CompanyService;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.CompanyDummyClient;
import com.luckylogistics.order.infrastructure.client.dto.GetCompanyClientResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyServiceAdapter implements CompanyService {
    private final CompanyFeignClient companyFeignClient;
   // private final CompanyDummyClient companyFeignClient;


//    @Override
//    public void isCompanyExists(UUID companyId) {
//        if(! (companyFeignClient.isCompanyExists(companyId))){
//            throw new RuntimeException("업체 연결정보가 존재하지 않습니다.");
//        }
//
//    }

	@Override
	public CompanyHubResponse getCompanyHub(UUID companyId) {
		ApiResponse<GetCompanyClientResponse> response = companyFeignClient.getCompany(companyId);
		if (!response.success()) {
			throw new RuntimeException(response.message());
		}
		return GetCompanyClientResponse.of(response.data());
	}

    @Override
    public CompanyResponse getCompanyUserId(Long userId) {
        ApiResponse<CompanyResponse> response = companyFeignClient.getCompanyUserId(userId);
        return response.data();
    }
}

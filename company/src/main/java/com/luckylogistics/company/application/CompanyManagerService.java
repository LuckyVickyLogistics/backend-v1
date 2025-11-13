package com.luckylogistics.company.application;

import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import com.luckylogistics.company.application.dto.CompanyManagerRequest;
import com.luckylogistics.company.application.dto.CompanyManagerResponse;
import com.luckylogistics.company.application.dto.CompanyResponse;
import com.luckylogistics.company.application.external.UserService;
import com.luckylogistics.company.domain.entity.CompanyManager;
import com.luckylogistics.company.domain.repository.CompanyManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyManagerService {
    private final CompanyManagerRepository companyManagerRepository;
    private final CompanyService companyService;
    private final UserService userService;

    public CompanyManagerResponse getCompanyManager(Long userId){
        CompanyManager companyManager = companyManagerRepository.findByUserId(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
        return CompanyManagerResponse.from(companyManager);
    }

    @Transactional
    public CompanyManagerResponse createCompanyManager(CompanyManagerRequest request) {
        userService.isUserExists(request.useId());
        companyService.validateCompany(request.companyId());

        CompanyManager companyManager = CompanyManager.create(request.useId(), request.companyId());
        companyManagerRepository.save(companyManager);
        return CompanyManagerResponse.from(companyManager);
    }

    // feignClient 용
    public CompanyResponse getCompanyUserId(Long userId) {
        CompanyManagerResponse result = getCompanyManager(userId);

        return companyService.getCompany(result.companyId());
    }
}

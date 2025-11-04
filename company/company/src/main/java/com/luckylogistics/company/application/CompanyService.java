package com.luckylogistics.company.application;

import com.luckylogistics.company.domain.entity.Company;
import com.luckylogistics.company.domain.CompanyRepository;
import com.luckylogistics.company.domain.CompanyDomainService;
import com.luckylogistics.company.application.dto.CompanyRequest;
import com.luckylogistics.company.application.dto.CompanyResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyDomainService domainService;

    public Page<CompanyResponse> getCompanies(String keyword, Pageable pageable) {
        //todo: 구현해야함
        return null;
    }

    public CompanyResponse getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId);
        return CompanyResponse.from(company);
    }

    @Transactional
    public CompanyResponse createCompany(CompanyRequest companyRequest) {
        // todo: 허브가 존재하는지 확인 > feignClient
        Company company = companyRequest.toEntity();
        companyRepository.save(company);
        return CompanyResponse.from(company);
    }

    @Transactional
    public void updateCompany(UUID companyId, CompanyRequest request) {
        // todo: 허브가 존재하는지 확인 > feignClient
        Company company = companyRepository.findById(companyId);
        company.update(request.name(), request.address(), request.type(), request.hubId());
    }

    @Transactional
    public void deleteCompany(Long userId, UUID companyId) {
        // todo: 허브가 존재하는지 확인 > feignClient
        Company company = companyRepository.findById(companyId);
        company.delete(userId);
    }
}

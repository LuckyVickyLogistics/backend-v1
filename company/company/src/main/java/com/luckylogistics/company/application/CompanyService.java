package com.luckylogistics.company.application;

import com.luckylogistics.company.application.dto.CompanyRequest;
import com.luckylogistics.company.application.dto.CompanyResponse;
import com.luckylogistics.company.application.external.HubService;
import com.luckylogistics.company.domain.CompanyDomainService;
import com.luckylogistics.company.domain.CompanyRepository;
import com.luckylogistics.company.domain.entity.Company;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyDomainService domainService;

    private final HubService hubService;

    public List<CompanyResponse> getCompanies(String name) {
        List<Company> result;
        if (name == null || name.isEmpty()) {
            result = companyRepository.findAll();
        }
        else{
            result = companyRepository.findByName(name);
        }
        return result
            .stream()
            .map(CompanyResponse::from)
            .toList();
    }

    public CompanyResponse getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId);
        return CompanyResponse.from(company);
    }

    @Transactional
    public CompanyResponse createCompany(CompanyRequest request) {
        hubService.isHubExists(request.hubId());
        Company company = request.toEntity();
        companyRepository.save(company);
        return CompanyResponse.from(company);
    }

    @Transactional
    public void updateCompany(UUID companyId, CompanyRequest request) {
        hubService.isHubExists(request.hubId());
        Company company = companyRepository.findById(companyId);
        company.update(request.name(), request.address(), request.type(), request.hubId());
    }

    @Transactional
    public void deleteCompany(Long userId, UUID companyId) {
        Company company = companyRepository.findById(companyId);
        company.delete(userId);
    }
}

package com.luckylogistics.company.application;

import com.luckylogistics.company.presentation.dto.CompanyRequest;
import com.luckylogistics.company.presentation.dto.CompanyResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    public Page<CompanyResponse> getCompanies(String keyword, Pageable pageable) {
        return null;
    }

    public CompanyResponse getCompany(UUID companyId) {
        return null;
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) {
    }

    public void updateCompany(UUID companyId) {
    }

    public void deleteCompany(UUID companyId) {
    }
}

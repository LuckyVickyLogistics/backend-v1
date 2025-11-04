package com.luckylogistics.company.infrastructure.repository;

import com.luckylogistics.company.domain.CompanyRepository;
import com.luckylogistics.company.domain.entity.Company;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {
    private final CompanyJpaRepository jpaRepository;

    @Override
    public Company findById(UUID companyId){
        return jpaRepository.findById(companyId)
            .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    @Override
    public void save(Company company) {
        jpaRepository.save(company);
    }

    @Override
    public List<Company> findAll(){
        return jpaRepository.findAllActive();
    }

    @Override
    public List<Company> findByName(String name){
        return jpaRepository.findByName(name);
    }
}

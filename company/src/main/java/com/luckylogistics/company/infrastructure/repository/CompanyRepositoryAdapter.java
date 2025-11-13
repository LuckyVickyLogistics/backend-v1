package com.luckylogistics.company.infrastructure.repository;

import com.luckylogistics.company.domain.repository.CompanyRepository;
import com.luckylogistics.company.domain.entity.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryAdapter implements CompanyRepository {
    private final CompanyJpaRepository jpaRepository;

    @Override
    public Optional<Company> findById(UUID companyId){
        return jpaRepository.findByCompanyIdAndDeletedAtIsNull(companyId);
    }

    @Override
    public void save(Company company) {
        jpaRepository.save(company);
    }

    @Override
    public List<Company> findAll(){
        return jpaRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<Company> findByName(String name){
        return jpaRepository.findByNameContainingIgnoreCaseAndDeletedAtIsNull(name);
    }
}

package com.luckylogistics.company.infrastructure.repository;

import com.luckylogistics.company.domain.entity.CompanyManager;
import com.luckylogistics.company.domain.repository.CompanyManagerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyManagerRepositoryAdapter implements CompanyManagerRepository {
    private final CompanyManagerJpaRepository jpaRepository;

    @Override
    public Optional<CompanyManager> findByUserId(Long userId){
        return jpaRepository.findByUserId(userId);
    }

    @Override
    public CompanyManager save(CompanyManager companyManager) {
        return jpaRepository.save(companyManager);
    }


}

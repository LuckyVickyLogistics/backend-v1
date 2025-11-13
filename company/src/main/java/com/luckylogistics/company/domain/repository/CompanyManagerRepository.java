package com.luckylogistics.company.domain.repository;

import com.luckylogistics.company.domain.entity.CompanyManager;
import java.util.Optional;

public interface CompanyManagerRepository {

    Optional<CompanyManager> findByUserId(Long userId);

    CompanyManager save(CompanyManager companyManager);
}

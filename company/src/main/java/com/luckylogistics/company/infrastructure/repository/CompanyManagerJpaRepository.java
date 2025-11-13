package com.luckylogistics.company.infrastructure.repository;

import com.luckylogistics.company.domain.entity.CompanyManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyManagerJpaRepository extends JpaRepository<CompanyManager, UUID> {
    Optional<CompanyManager> findByUserId(Long userId);

    CompanyManager save(CompanyManager companyManager);

}

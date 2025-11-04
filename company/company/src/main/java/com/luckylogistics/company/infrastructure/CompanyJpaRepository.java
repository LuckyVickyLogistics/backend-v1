package com.luckylogistics.company.infrastructure;

import com.luckylogistics.company.domain.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID> {
}

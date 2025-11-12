package com.luckylogistics.company.infrastructure.repository;

import com.luckylogistics.company.domain.entity.Company;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID> {

    List<Company> findAllByDeletedAtIsNull();

    @Query("""
      select c from Company c
      where (:name is null or lower(c.name) like lower(concat('%', :name, '%')))
      and c.deletedAt is null
    """)
    List<Company> findByNameContainingIgnoreCaseAndDeletedAtIsNull(String name);
}

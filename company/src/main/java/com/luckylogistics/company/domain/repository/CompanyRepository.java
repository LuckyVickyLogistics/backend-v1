package com.luckylogistics.company.domain.repository;

import com.luckylogistics.company.domain.entity.Company;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findById(UUID id);

    void save(Company company);

    List<Company> findAll();

    List<Company> findByName(String name);
}

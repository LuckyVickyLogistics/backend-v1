package com.luckylogistics.company.domain;

import com.luckylogistics.company.domain.entity.Company;
import java.util.UUID;

public interface CompanyRepository {
    Company findById(UUID id);
    void save(Company company);

}

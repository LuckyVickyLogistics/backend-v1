package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.product.application.external.CompanyService;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CompanyDummyClient {

    public boolean isCompanyIdExists(UUID companyId) {
        return true;
    }
}

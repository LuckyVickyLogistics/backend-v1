package com.luckylogistics.order.infrastructure.client;


import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CompanyDummyClient {
    public boolean isCompanyExists(UUID companyId) {
        return true;
    }
}

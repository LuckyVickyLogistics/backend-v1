package com.luckylogistics.company.infrastructure.config;

import com.luckylogistics.company.domain.CompanyDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public CompanyDomainService companyDomainService() {
        return new CompanyDomainService();
    }
}

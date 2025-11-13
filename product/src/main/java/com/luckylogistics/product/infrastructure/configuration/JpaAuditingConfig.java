package com.luckylogistics.product.infrastructure.configuration;

import com.luckylogistics.common.infrastructure.util.HeaderAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "headerAuditorAware")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<Long> headerAuditorAware() {
        return new HeaderAuditorAware();
    }
}
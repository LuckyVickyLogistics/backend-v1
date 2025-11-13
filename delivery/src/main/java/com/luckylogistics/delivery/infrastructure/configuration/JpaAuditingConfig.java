package com.luckylogistics.delivery.infrastructure.configuration;

import com.luckylogistics.common.infrastructure.util.HeaderAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing 설정
 * X-User-Id 헤더를 createdBy, updatedBy에 자동 주입
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "headerAuditorAware")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<Long> headerAuditorAware() {
        return new HeaderAuditorAware();
    }
}
package com.luckylogistics.common.infrastructure.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * JPA Auditing 설정
 * X-User-Id 헤더를 createdBy, updatedBy에 자동 주입
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                String userIdHeader = attributes.getRequest().getHeader("X-User-Id");
                if (userIdHeader != null) {
                    try {
                        return Optional.of(Long.parseLong(userIdHeader));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                }
            }

            return Optional.empty();
        };
    }
}
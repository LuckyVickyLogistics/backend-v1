package com.luckylogistics.company.infrastructure.config;

import com.luckylogistics.common.infrastructure.util.HeaderAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "headerAuditorAware")
public class JpaConfig {

    @Bean
    public AuditorAware<Long> headerAuditorAware() {
        return new HeaderAuditorAware();
    }

}

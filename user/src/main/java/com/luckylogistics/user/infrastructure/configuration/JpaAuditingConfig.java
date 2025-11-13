package com.luckylogistics.user.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.luckylogistics.common.infrastructure.util.HeaderAuditorAware;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "headerAuditorAware")
public class JpaAuditingConfig {

	@Bean
	public AuditorAware<Long> headerAuditorAware() {
		return new HeaderAuditorAware();
	}
}
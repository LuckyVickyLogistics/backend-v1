package com.luckylogistics.slack.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

			if (requestAttributes != null) {
				HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

				String token = request.getHeader("Authorization");
				if (token != null && !token.isEmpty()) {
					template.header(HttpHeaders.AUTHORIZATION, token);
				}
			}
		};
	}

}

package com.luckylogistics.common.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (requestAttributes == null) return;

            HttpServletRequest request = requestAttributes.getRequest();

            // Authorization 헤더 전파
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null && !token.isEmpty()) {
                template.header(HttpHeaders.AUTHORIZATION, token);
            }

            // X-User-Id 헤더 전파
            String userId = request.getHeader("X-User-Id");
            if (userId != null && !userId.isEmpty()) {
                template.header("X-User-Id", userId);
            }

            // X-User-Role헤더 전파
            String userRole = request.getHeader("X-User-Role");
            if (userRole != null && !userRole.isEmpty()) {
                template.header("X-User-Role", userRole);
            }
        };
    }

    @Bean
    public feign.codec.Decoder customDecoder(ObjectMapper objectMapper) {
        return (response, type) -> {
            try (var reader = response.body().asReader()) {
                // 제네릭 타입 (ApiResponse<Response>) 파싱 가능하게 처리
                return objectMapper.readValue(reader, objectMapper.constructType(type));
            }
        };
    }
}
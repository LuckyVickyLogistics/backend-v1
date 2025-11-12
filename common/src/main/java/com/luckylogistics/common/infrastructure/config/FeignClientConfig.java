package com.luckylogistics.common.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
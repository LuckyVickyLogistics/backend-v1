package com.luckylogistics.common.infrastructure.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * JPA Auditing 설정 X-User-Id 헤더를 createdBy, updatedBy에 자동 주입
 */
@Component
public class HeaderAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
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
    }
}
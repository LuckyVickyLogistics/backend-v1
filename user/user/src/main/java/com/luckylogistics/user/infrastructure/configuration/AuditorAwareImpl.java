package com.luckylogistics.user.infrastructure.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO: 헤더 값 가져오기
		// 로그인된 사용자의 username 반환
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}

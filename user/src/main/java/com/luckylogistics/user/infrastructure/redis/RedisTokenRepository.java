package com.luckylogistics.user.infrastructure.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisTokenRepository {

	private final RedisTemplate<String, Object> redisTemplate;

	// RefreshToken 저장
	public void saveRefreshToken(String username, String token, Long expireTime) {
		redisTemplate.opsForValue().set(
			"RT:" + username, token, expireTime, TimeUnit.MILLISECONDS
		);
		log.info("[REDIS] saveRefreshToken -> {} = {}", username, token);
	}

	// RefreshToken 조회
	public String getRefreshToken(String username) {
		log.info("[REDIS] getRefreshToken -> {}", username);
		return (String) redisTemplate.opsForValue().get("RT:" + username);
	}

	// RefreshToken 삭제(로그아웃 시)
	public void deleteRefreshToken(String username) {
		redisTemplate.delete("RT:" + username);
		log.info("[REDIS] deleteRefreshToken -> {}", username);
	}

	// AccessToken 블랙리스트 등록
	public void blacklistAccessToken(String token, Long expirationTime) {
		redisTemplate.opsForValue().set(
			"BL:" + token, "logout", expirationTime, TimeUnit.MILLISECONDS
		);
		log.info("[REDIS] blacklistAccessToken -> {}", token);
	}

	// 블랙리스트 여부 확인
	public boolean isBlacklisted(String token) {
		log.info("[REDIS] isBlacklisted -> {}", token);
		return redisTemplate.hasKey("BL:" + token);
	}
}
package com.luckylogistics.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.user.application.dto.LoginCommand;
import com.luckylogistics.user.application.dto.TokenResponse;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;
import com.luckylogistics.user.infrastructure.jwt.JwtProvider;
import com.luckylogistics.user.infrastructure.redis.RedisTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder  passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RedisTokenRepository redisRepository;

	public TokenResponse login(LoginCommand command) {
		User user = userRepository.findByUsername(command.username())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		if (!passwordEncoder.matches(command.password(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = jwtProvider.generateAccessToken(user.getUsername(), user.getRole().name());
		String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

		redisRepository.saveRefreshToken(user.getUsername(), refreshToken, 7 * 24 * 60 * 60 * 1000L);

		return new TokenResponse(accessToken, refreshToken);
	}

	public TokenResponse reissue(String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new IllegalArgumentException("리프레시 토큰이 전달되지 않았습니다.");
		}

		if (!jwtProvider.validateToken(refreshToken)) {
			throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
		}

		String username = jwtProvider.getUsernameFromToken(refreshToken);
		String savedToken = redisRepository.getRefreshToken(username);

		if (!refreshToken.equals(savedToken)) {
			throw new IllegalArgumentException("만료되었거나 로그아웃된 토큰입니다.");
		}

		String newAccessToken = jwtProvider.generateAccessToken(username, "USER");
		return new TokenResponse(newAccessToken, refreshToken);
	}

	public void logout(String accessToken) {
		String username = jwtProvider.getUsernameFromToken(accessToken);
		redisRepository.deleteRefreshToken(username);

		Long expiration = jwtProvider.getRemainingMillis(accessToken);

		redisRepository.blacklistAccessToken(accessToken, expiration);
	}
}

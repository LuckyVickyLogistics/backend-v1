package com.luckylogistics.user.application.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.user.application.dto.LoginCommand;
import com.luckylogistics.user.application.dto.TokenResponse;
import com.luckylogistics.user.common.exception.BusinessException;
import com.luckylogistics.user.common.exception.ErrorCode;
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
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

/*		if (user.getStatus() != Status.APPROVED) {
			throw new IllegalArgumentException("승인되지 않은 사용자입니다.");
		}*/

		if (!passwordEncoder.matches(command.password(), user.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_PASSWORD);
		}

		String accessToken = jwtProvider.generateAccessToken(
			user.getIdentifier().toString(),
			user.getUsername(),
			user.getRole().toString()
		);
		String refreshToken = jwtProvider.generateRefreshToken(user.getIdentifier().toString());

		redisRepository.saveRefreshToken(
			user.getIdentifier().toString(),
			refreshToken,
			jwtProvider.getRefreshTokenValidity()
		);

		return new TokenResponse(accessToken, refreshToken);
	}

	public TokenResponse reissue(String refreshToken) {
		/*if (refreshToken == null || refreshToken.isBlank()) {
			throw new IllegalArgumentException("리프레시 토큰이 전달되지 않았습니다.");
		}*/

		if (!jwtProvider.validateToken(refreshToken)) {
			throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
		}

		UUID identifier = jwtProvider.getIdentifierFromToken(refreshToken);
		String savedToken = redisRepository.getRefreshToken(identifier.toString());

		if (!refreshToken.equals(savedToken)) {
			throw new BusinessException(ErrorCode.LOGGED_OUT_TOKEN);
		}

		User user = userRepository.findByIdentifier(identifier)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		String newAccessToken = jwtProvider.generateAccessToken(
			user.getIdentifier().toString(),
			user.getUsername(),
			user.getRole().toString()
		);
		return new TokenResponse(newAccessToken, refreshToken);
	}

	public void logout(String accessToken) {
		UUID identifier = jwtProvider.getIdentifierFromToken(accessToken);
		redisRepository.deleteRefreshToken(identifier.toString());

		Long expiration = jwtProvider.getRemainingMillis(accessToken);
		redisRepository.blacklistAccessToken(accessToken, expiration);
	}
}

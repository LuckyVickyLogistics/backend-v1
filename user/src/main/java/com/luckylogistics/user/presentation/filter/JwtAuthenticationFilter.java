package com.luckylogistics.user.presentation.filter;

import java.io.IOException;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.luckylogistics.user.infrastructure.jwt.JwtProvider;
import com.luckylogistics.user.infrastructure.redis.RedisTokenRepository;
import com.luckylogistics.user.infrastructure.security.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final RedisTokenRepository redisRepository;
	private final CustomUserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
		throws ServletException, IOException {

		String token = jwtProvider.resolveToken(request);

		if (token != null && jwtProvider.validateToken(token) && !redisRepository.isBlacklisted(token)) {
			UUID identifier = jwtProvider.getIdentifierFromToken(token);
			UserDetails userDetails = userDetailService.loadUserByUsername(identifier.toString());

			UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}
}

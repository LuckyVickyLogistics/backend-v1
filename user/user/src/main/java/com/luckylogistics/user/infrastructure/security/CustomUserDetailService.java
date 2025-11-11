package com.luckylogistics.user.infrastructure.security;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String identifierString) throws UsernameNotFoundException {
		UUID identifier = UUID.fromString(identifierString);
		User user = userRepository.findByIdentifier(identifier)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + identifier));
		return new UserAdapter(user);
	}
}

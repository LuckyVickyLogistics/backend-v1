package com.luckylogistics.user.infrastructure.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.luckylogistics.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdapter  implements UserDetails {

	private final User user;

	public User getDomainUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getIdentifier().toString();
	}
}

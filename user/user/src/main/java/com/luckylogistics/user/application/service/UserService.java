package com.luckylogistics.user.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.common.exception.BusinessException;
import com.luckylogistics.user.common.exception.ErrorCode;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long signup(SignupCommand command) {

		if (userRepository.existsByUsername(command.username())) {
			throw new BusinessException(ErrorCode.DUPLICATE_USER);
		}

		String encodedPassword = passwordEncoder.encode(command.password());

		SignupCommand encodedCommand = SignupCommand.builder()
			.username(command.username())
			.password(encodedPassword)
			.slackId(command.slackId())
			.role(command.role())
			.organizationType(command.organizationType())
			.build();

		User user = User.createPendingUser(encodedCommand);
		return userRepository.save(user).getUserId();
	}
}

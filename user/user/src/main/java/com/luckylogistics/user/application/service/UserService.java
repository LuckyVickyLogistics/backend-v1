package com.luckylogistics.user.application.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.common.exception.BusinessException;
import com.luckylogistics.user.common.exception.ErrorCode;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.domain.model.OrganizationType;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;
import com.luckylogistics.user.infrastructure.client.CompanyClient;
import com.luckylogistics.user.infrastructure.client.CompanyResponse;
import com.luckylogistics.user.infrastructure.client.HubClient;
import com.luckylogistics.user.infrastructure.client.HubResponse;
import com.luckylogistics.user.presentation.request.UserStatusUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final HubClient hubClient;
	private final CompanyClient companyClient;

	@Transactional
	public Long signup(SignupCommand command) {

		// username(로그인 id) 중복확인
		if (userRepository.existsByUsername(command.username())) {
			throw new BusinessException(ErrorCode.DUPLICATE_USER);
		}

		// 외부 컨텍스트 조회
		if (command.organizationType().equals(OrganizationType.HUB)) {
			ApiResponse<HubResponse> response = hubClient.getHubById(command.organizationId());

			if (response == null || response.data() == null)
				throw new BusinessException(ErrorCode.HUB_NOT_FOUND);

		} else if (command.organizationType().equals(OrganizationType.COMPANY)) {
			ResponseEntity<CompanyResponse> response = companyClient.getCompany(command.organizationId());

			if (response == null)
				throw new BusinessException(ErrorCode.COMPANY_NOT_FOUND);
		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(command.password());

		SignupCommand encodedCommand = SignupCommand.builder()
			.username(command.username())
			.password(encodedPassword)
			.slackId(command.slackId())
			.organizationType(command.organizationType())
			.organizationId(command.organizationId())
			//.organizationName(command.organizationName())
			.build();

		User user = User.createPendingUser(encodedCommand);
		return userRepository.save(user).getUserId();
	}

	public void updateStatus(Long userId, UserStatusUpdateRequest request) {

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		switch (request.status().toUpperCase()) {
			case "APPROVED" -> {
				if (request.role() == null) {
					throw new BusinessException(ErrorCode.USER_ROLE_REQUIRED);
				}
				user.approve(request.role());
			}
			case "REJECTED" -> {
				user.reject();
			}
			default -> throw new BusinessException(ErrorCode.INVALID_USER_STATUS);
		}
	}
}

package com.luckylogistics.user.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.application.dto.UserDeactiveCommand;
import com.luckylogistics.user.application.dto.UserUpdateCommand;
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
import com.luckylogistics.user.infrastructure.jwt.JwtProvider;
import com.luckylogistics.user.presentation.request.UserStatusUpdateRequest;
import com.luckylogistics.user.presentation.response.UserDetailResponse;
import com.luckylogistics.user.presentation.response.UserListResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final HubClient hubClient;
	private final CompanyClient companyClient;
	private final JwtProvider jwtProvider;

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

	// 내 정보 조회
	public UserDetailResponse getMyInfo(HttpServletRequest request) {
		String token = jwtProvider.resolveToken(request);
		UUID identifier = jwtProvider.getIdentifierFromToken(token);

		User user = userRepository.findByIdentifier(identifier)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		return UserDetailResponse.from(user);
	}

	// 내 정보 수정
	public void updateUser(UserUpdateCommand command) {
		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		user.updateInfo(command.slackId());
	}


	// 전체 회원 조회
	public List<UserListResponse> getAllUsers() {
		return userRepository.findAll().stream()
			.map(UserListResponse::from)
			.toList();
	}


	// 회원 상세 조회
	public UserDetailResponse getUserById(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		return UserDetailResponse.from(user);
	}

	// 회원 비활성화
	public void deactiveUser(UserDeactiveCommand command) {
		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		if (user.isDeleted() == true) {
			throw new BusinessException(ErrorCode.ALREADY_PROCESSED_USER);
		}

		user.markDeleted("");
	}

}

package com.luckylogistics.user.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.application.dto.UserDeactiveCommand;
import com.luckylogistics.user.application.dto.UserResponse;
import com.luckylogistics.user.application.dto.UserUpdateCommand;
import com.luckylogistics.user.application.external.CompanyService;
import com.luckylogistics.user.application.external.HubService;
import com.luckylogistics.user.common.exception.BusinessException;
import com.luckylogistics.user.common.exception.ErrorCode;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.domain.model.OrganizationType;
import com.luckylogistics.user.domain.model.Status;
import com.luckylogistics.user.domain.model.User;
import com.luckylogistics.user.domain.repository.UserRepository;
import com.luckylogistics.user.infrastructure.client.CompanyResponse;
import com.luckylogistics.user.infrastructure.client.HubResponse;
import com.luckylogistics.user.infrastructure.jwt.JwtProvider;
import com.luckylogistics.user.presentation.request.UserStatusUpdateRequest;
import com.luckylogistics.user.presentation.response.UserDetailResponse;
import com.luckylogistics.user.presentation.response.UserListResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	private final HubService hubService;
	private final CompanyService companyService;

	public String signup(SignupCommand command) {

		// 아이디(username) 중복확인
		if (userRepository.existsByUsername(command.username())) {
			throw new BusinessException(ErrorCode.DUPLICATE_USER);
		}

		// 외부 컨텍스트 조회
		String organizationName = null;

		if (command.organizationType().equals(OrganizationType.HUB)) {
			ApiResponse<HubResponse> response = hubService.getHubById(command.organizationId());

			if (response == null || response.data() == null)
				throw new BusinessException(ErrorCode.HUB_NOT_FOUND);

			organizationName = response.data().name();

		} else if (command.organizationType().equals(OrganizationType.COMPANY)) {
			CompanyResponse response = companyService.getCompany(command.organizationId());

			if (response == null)
				throw new BusinessException(ErrorCode.COMPANY_NOT_FOUND);

			organizationName = response.name();
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

		return userRepository.save(user).getUsername();
	}

	// 회원가입 요청 처리 (master, hub)
	public UserResponse updateStatus(Long userId, UserStatusUpdateRequest request, Long currentUserId, UserRole currentUserRole) {

		// 유저가 존재하는지 확인
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		// 이미 처리된 회원인지 확인
		if (!user.getStatus().equals(Status.PENDING)) {
			throw new BusinessException(ErrorCode.ALREADY_PROCESSED_USER);
		}

		// 권한 아닌 사람은 throw
		if (currentUserRole.isDeliveryManager() || currentUserRole.isCompanyManager()) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		// 상태별 처리
		switch (request.status()) {
			case APPROVED -> {
				// 승인 시 role 값 필요
				if (request.role() == null) {
					throw new BusinessException(ErrorCode.USER_ROLE_REQUIRED);
				}
				user.approve(currentUserRole);

			}

			case REJECTED -> user.reject();

			default -> throw new BusinessException(ErrorCode.INVALID_USER_STATUS);
		}

		return UserResponse.from(user);
	}

	// 내 정보 조회
	public UserResponse getMyInfo(HttpServletRequest request) {
		String token = jwtProvider.resolveToken(request);
		UUID identifier = jwtProvider.getIdentifierFromToken(token);

		User user = userRepository.findByIdentifier(identifier)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		return UserResponse.from(user);
	}

	// 정보 수정 (master)
	public UserResponse updateUser(UserUpdateCommand command, UserRole currentUserRole) {

		if (!currentUserRole.isMaster()) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		user.updateInfo(command.slackId());

		return UserResponse.from(user);
	}


	// 전체 회원 조회 (master)
	public List<UserListResponse> getAllUsers(UserRole currentUserRole) {

		if (!currentUserRole.isMaster()) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		// 활성화 상태의 회원 목록 조회
		return userRepository.findAllByIsDeletedFalse().stream()
			.map(UserListResponse::from)
			.toList();
	}


	// 회원 상세 조회 (master)
	public UserDetailResponse getUserById(Long userId, UserRole currentUserRole) {

		if (!currentUserRole.isMaster()) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

		return UserDetailResponse.from(user);
	}

	// 회원 비활성화 (master)
	public void deactiveUser(UserDeactiveCommand command, HttpServletRequest request, UserRole currentUserRole) {

		if (!currentUserRole.isMaster()) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		User user = userRepository.findById(command.userId())
			.orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));


		if (user.isDeleted() == true) {
			throw new BusinessException(ErrorCode.ALREADY_PROCESSED_USER);
		}

		user.markDeleted(user.getUserId().toString());
	}

}

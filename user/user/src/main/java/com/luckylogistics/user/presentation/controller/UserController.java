package com.luckylogistics.user.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.user.application.dto.SignupCommand;
import com.luckylogistics.user.application.dto.UserDeactiveCommand;
import com.luckylogistics.user.application.dto.UserResponse;
import com.luckylogistics.user.application.dto.UserUpdateCommand;
import com.luckylogistics.user.application.service.UserService;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.presentation.request.UserSignupRequest;
import com.luckylogistics.user.presentation.request.UserStatusUpdateRequest;
import com.luckylogistics.user.presentation.request.UserUpdateRequest;
import com.luckylogistics.user.presentation.response.UserDetailResponse;
import com.luckylogistics.user.presentation.response.UserListResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody UserSignupRequest request) {

		SignupCommand command = SignupCommand.builder()
			.username(request.username())
			.password(request.password())
			.slackId(request.slackId())
			.organizationType(request.organizationType())
			//.organizationName(request.organizationName())
			.build();

		UserResponse userResponse = userService.signup(command);

		return ResponseEntity.ok(ApiResponse.success(userResponse, "회원가입이 요청 되었습니다."));
	}

	// 회원가입 요청 처리 (master, hub)
	@PutMapping("/{userId}/status")
	public ResponseEntity<ApiResponse<UserResponse>> updateStatus(
		@PathVariable Long userId,
		@RequestBody UserStatusUpdateRequest request
	) {
		UserResponse updatedUser = userService.updateStatus(userId, request);
		return ResponseEntity.ok(ApiResponse.success(updatedUser, "회원가입 요청을 처리했습니다."));
	}

	// 내 정보 조회
	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(HttpServletRequest request) {
		UserResponse myInfo = userService.getMyInfo(request);
		return ResponseEntity.ok(ApiResponse.success(myInfo, "내 정보 조회를 완료했습니다."));
	}

	// 내 정보 수정
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(
		@PathVariable Long userId,
		@RequestBody @Valid UserUpdateRequest request
	) {
		UserResponse updatedUser = userService.updateUser(
			UserUpdateCommand.builder()
				.userId(userId)
				.slackId(request.slackId())
				.build()
		);
		return ResponseEntity.ok(ApiResponse.success(updatedUser, "회원 정보를 수정했습니다."));
	}

	// 전체 회원 조회 (master)
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserListResponse>>> getAllUsers() {
		List<UserListResponse> users = userService.getAllUsers();
		return ResponseEntity.ok(ApiResponse.success(users, "전체 회원 목록을 조회를 완료했습니다."));
	}

	// 회원 상세 조회 (master)
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(@PathVariable Long userId) {
		UserDetailResponse user = userService.getUserById(userId);
		return ResponseEntity.ok(ApiResponse.success(user, "회원 상세 조회를 완료했습니다."));
	}

	// 회원 비활성화 (master)
	@PutMapping("/{userId}/deactivate")
	public ResponseEntity<ApiResponse<Void>> deactiveUser(@PathVariable Long userId, HttpServletRequest request) {
		userService.deactiveUser(UserDeactiveCommand.builder().userId(userId).build(), request);
		return ResponseEntity.ok(ApiResponse.success("회원이 비활성화 되었습니다."));
	}
}

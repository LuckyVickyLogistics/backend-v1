package com.luckylogistics.slack.presentation.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.slack.application.dto.EmailCheckCommand;
import com.luckylogistics.slack.application.dto.SlackEmailCheckResult;
import com.luckylogistics.slack.application.dto.SlackMessageResult;
import com.luckylogistics.slack.application.dto.StatusUpdateCommand;
import com.luckylogistics.slack.application.service.SlackService;
import com.luckylogistics.slack.common.response.ApiResponse;
import com.luckylogistics.slack.domain.vo.Status;
import com.luckylogistics.slack.presentation.dto.SlackCheckInWorkSpaceResponse;
import com.luckylogistics.slack.presentation.dto.SlackCheckInWorkspaceRequest;
import com.luckylogistics.slack.presentation.dto.SlackDetailResponse;
import com.luckylogistics.slack.presentation.dto.SlackStatusUpdateRequest;
import com.luckylogistics.slack.presentation.dto.SlackSummaryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/slack-messages")
@RequiredArgsConstructor
@Tag(name = "Slack API")
public class SlackController {

	private final SlackService slackService;

	// TODO: 페이징 및 검색 구현
	@Operation(summary = "슬랙 메시지 목록 조회", description = "마스터 관리자만 사용 가능합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<SlackSummaryResponse>>> getAllMessages(
		@RequestParam(required = false) String receiverEmail,
		@RequestParam(required = false) Status status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "DESC") Sort.Direction direction
	) {
		Page<SlackSummaryResponse> responseDtoPage = slackService.getAllMessages(receiverEmail, status, page, size, sortBy, direction)
			.map(SlackSummaryResponse::from);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지 목록이 조회되었습니다.", responseDtoPage));
	}

	@Operation(summary = "슬랙 메시지 조회", description = "마스터 관리자만 사용 가능합니다.")
	@GetMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<SlackDetailResponse>> getMessage(@PathVariable UUID slackMessageId) {
		SlackMessageResult result = slackService.getMessage(slackMessageId);
		SlackDetailResponse responseDto = SlackDetailResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지가 조회되었습니다.", responseDto));
	}

	@Operation(summary = "슬랙 워크스페이스 가입 여부 확인", description = "모두 사용 가능합니다.")
	@GetMapping("/workspaces")
	public ResponseEntity<ApiResponse<SlackCheckInWorkSpaceResponse>> checkInWorkspace(
		@Valid @RequestBody SlackCheckInWorkspaceRequest requestDto
	) {
		EmailCheckCommand command = SlackCheckInWorkspaceRequest.of(requestDto);
		SlackEmailCheckResult result = slackService.checkInWorkspace(command);
		SlackCheckInWorkSpaceResponse responseDto = SlackCheckInWorkSpaceResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 워크스페이스 가입 여부가 확인되었습니다.", responseDto));
	}

	@Operation(summary = "슬랙 메시지 상태 수정", description = "마스터 관리자만 사용 가능합니다.")
	@PutMapping("/{slackMessageId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatus(
		@PathVariable UUID slackMessageId, @Valid @RequestBody SlackStatusUpdateRequest requestDto
	) {
		StatusUpdateCommand command = SlackStatusUpdateRequest.of(requestDto);
		slackService.updateStatus(slackMessageId, command);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지의 상태가 수정되었습니다."));
	}

	@Operation(summary = "슬랙 메시지 삭제", description = "마스터 관리자만 사용 가능합니다.")
	@DeleteMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable UUID slackMessageId) {
		slackService.deleteMessage(slackMessageId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Slack 메시지가 삭제되었습니다."));
	}

}

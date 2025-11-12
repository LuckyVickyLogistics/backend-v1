package com.luckylogistics.slack.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.slack.application.dto.EmailCheckCommand;
import com.luckylogistics.slack.application.dto.SlackEmailCheckResult;
import com.luckylogistics.slack.application.dto.SlackMessageResult;
import com.luckylogistics.slack.application.dto.StatusUpdateCommand;
import com.luckylogistics.slack.application.service.SlackService;
import com.luckylogistics.slack.common.response.ApiResponse;
import com.luckylogistics.slack.presentation.dto.SlackCheckInWorkSpaceResponse;
import com.luckylogistics.slack.presentation.dto.SlackCheckInWorkspaceRequest;
import com.luckylogistics.slack.presentation.dto.SlackDetailResponse;
import com.luckylogistics.slack.presentation.dto.SlackStatusUpdateRequest;
import com.luckylogistics.slack.presentation.dto.SlackSummaryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/slack-messages")
@RequiredArgsConstructor
public class SlackController {

	private final SlackService slackService;

	// TODO: 페이징 및 검색 구현
	@GetMapping
	public ResponseEntity<ApiResponse<List<SlackSummaryResponse>>> getAllMessages() {
		List<SlackMessageResult> resultList = slackService.getAllMessages();
		List<SlackSummaryResponse> responseDtoList = resultList.stream()
			.map(SlackSummaryResponse::from)
			.toList();

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지 목록이 조회되었습니다.", responseDtoList));
	}

	@GetMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<SlackDetailResponse>> getMessage(@PathVariable UUID slackMessageId) {
		SlackMessageResult result = slackService.getMessage(slackMessageId);
		SlackDetailResponse responseDto = SlackDetailResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지가 조회되었습니다.", responseDto));
	}

	@GetMapping("/workspaces")
	public ResponseEntity<ApiResponse<SlackCheckInWorkSpaceResponse>> checkInWorkspace(
		@Valid @RequestBody SlackCheckInWorkspaceRequest requestDto
	) {
		EmailCheckCommand command = SlackCheckInWorkspaceRequest.of(requestDto);
		SlackEmailCheckResult result = slackService.checkInWorkspace(command);
		SlackCheckInWorkSpaceResponse responseDto = SlackCheckInWorkSpaceResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 워크스페이스 가입 여부가 확인되었습니다.", responseDto));
	}

	@PatchMapping("/{slackMessageId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatus(
		@PathVariable UUID slackMessageId, @Valid @RequestBody SlackStatusUpdateRequest requestDto
	) {
		StatusUpdateCommand command = SlackStatusUpdateRequest.of(requestDto);
		slackService.updateStatus(slackMessageId, command);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Slack 메시지의 상태가 수정되었습니다."));
	}

	@DeleteMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable UUID slackMessageId) {
		slackService.deleteMessage(slackMessageId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Slack 메시지가 삭제되었습니다."));
	}

}

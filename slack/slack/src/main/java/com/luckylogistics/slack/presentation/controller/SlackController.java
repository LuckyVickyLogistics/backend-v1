package com.luckylogistics.slack.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.slack.application.command.EmailCheckCommand;
import com.luckylogistics.slack.application.command.StatusUpdateCommand;
import com.luckylogistics.slack.application.result.SlackEmailCheckResult;
import com.luckylogistics.slack.application.result.SlackMessageResult;
import com.luckylogistics.slack.application.service.SlackService;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;
import com.luckylogistics.slack.presentation.ApiResponse;
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

	// TODO: 테스트용 이벤트 생성 메서드로, 주문 서비스 개발 완료 후 삭제
	@PostMapping("/publish")
	public ResponseEntity<ApiResponse<Void>> publish(@RequestBody OrderCreatedEvent requestDto) {
		slackService.publish(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
	}

	// TODO: 페이징 및 검색 구현
	@GetMapping
	public ResponseEntity<ApiResponse<List<SlackSummaryResponse>>> getAllMessages() {
		List<SlackMessageResult> resultList = slackService.getAllMessages();
		List<SlackSummaryResponse> responseDtoList = resultList.stream()
			.map(result -> SlackSummaryResponse.builder()
				.slackMessageId(result.slackMessageId())
				.receiverEmail(result.receiverEmail())
				.status(result.status().name())
				.build())
			.toList();

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDtoList));
	}

	@GetMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<SlackDetailResponse>> getMessage(@PathVariable UUID slackMessageId) {
		SlackMessageResult result = slackService.getMessage(slackMessageId);
		SlackDetailResponse responseDto = SlackDetailResponse.builder()
			.slackMessageId(result.slackMessageId())
			.receiverEmail(result.receiverEmail())
			.content(result.content())
			.status(result.status().name())
			.createdAt(result.createdAt())
			.createdBy(result.createdBy())
			.updatedAt(result.updatedAt())
			.updatedBy(result.updatedBy())
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDto));
	}

	@GetMapping("/workspaces")
	public ResponseEntity<ApiResponse<SlackCheckInWorkSpaceResponse>> checkInWorkspace(
		@Valid @RequestBody SlackCheckInWorkspaceRequest requestDto
	) {
		EmailCheckCommand command = EmailCheckCommand.builder()
			.email(requestDto.email())
			.build();
		SlackEmailCheckResult result = slackService.checkInWorkspace(command);
		SlackCheckInWorkSpaceResponse responseDto = SlackCheckInWorkSpaceResponse.builder()
			.exists(result.exists())
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDto));
	}

	@PatchMapping("/{slackMessageId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatus(
		@PathVariable UUID slackMessageId, @Valid @RequestBody SlackStatusUpdateRequest requestDto
	) {
		StatusUpdateCommand command = StatusUpdateCommand.builder()
			.status(requestDto.status())
			.build();
		slackService.updateStatus(slackMessageId, command);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
	}

	@DeleteMapping("/{slackMessageId}")
	public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable UUID slackMessageId) {
		slackService.deleteMessage(slackMessageId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success());
	}

}

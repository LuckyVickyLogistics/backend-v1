package com.luckylogistics.ai.presentation.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptReadResult;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.dto.StatusUpdateCommand;
import com.luckylogistics.ai.application.service.AiService;
import com.luckylogistics.ai.common.response.ApiResponse;
import com.luckylogistics.ai.domain.vo.Status;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedRequest;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptDetailResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptStatusUpdateRequest;
import com.luckylogistics.ai.presentation.dto.AiPromptSummaryResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-prompts")
@RequiredArgsConstructor
@Tag(name = "Ai API")
public class AiController {

	private final AiService aiService;

	@Operation(summary = "AI 프롬프트 생성", description = "모두 사용 가능합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<AiPromptCreatedResponse>> createAiPrompt(
		@Valid @RequestBody AiPromptCreatedRequest requestDto
	) {
		AiPromptCreatedCommand command = AiPromptCreatedRequest.of(requestDto);
		AiPromptResult result = aiService.createAiPrompt(command);
		AiPromptCreatedResponse responseDto = AiPromptCreatedResponse.from(result);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("AI 프롬프트가 생성되었습니다.", responseDto));
	}

	@Operation(summary = "AI 프롬프트 목록 조회", description = "마스터 관리자만 사용 가능합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<AiPromptSummaryResponse>>> getAllPrompts(
		@RequestParam(required = false) Status status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy,
		@RequestParam(defaultValue = "DESC") Sort.Direction direction
	) {
		Page<AiPromptSummaryResponse> responseDtoPage = aiService.getAllPrompts(status, page, size, sortBy, direction)
			.map(AiPromptSummaryResponse::from);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("AI 프롬프트 목록이 조회되었습니다.", responseDtoPage));
	}

	@Operation(summary = "AI 프롬프트 조회", description = "마스터 관리자만 사용 가능합니다.")
	@GetMapping("/{aiPromptId}")
	public ResponseEntity<ApiResponse<AiPromptDetailResponse>> getPrompt(@PathVariable UUID aiPromptId) {
		AiPromptReadResult result = aiService.getPrompt(aiPromptId);
		AiPromptDetailResponse responseDto = AiPromptDetailResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("AI 프롬프트가 조회되었습니다.", responseDto));
	}

	@Operation(summary = "AI 프롬프트 상태 수정", description = "마스터 관리자만 사용 가능합니다.")
	@PutMapping("/{aiPromptId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatus(
		@PathVariable UUID aiPromptId, @Valid @RequestBody AiPromptStatusUpdateRequest requestDto
	) {
		StatusUpdateCommand command = AiPromptStatusUpdateRequest.of(requestDto);
		aiService.updateStatus(aiPromptId, command);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("AI 프롬프트의 상태가 수정되었습니다."));
	}

	@Operation(summary = "AI 프롬프트 삭제", description = "마스터 관리자만 사용 가능합니다.")
	@DeleteMapping("/{aiPromptId}")
	public ResponseEntity<ApiResponse<Void>> deletePrompt(@PathVariable UUID aiPromptId) {
		aiService.deletePrompt(aiPromptId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("AI 프롬프트가 삭제되었습니다."));
	}

}

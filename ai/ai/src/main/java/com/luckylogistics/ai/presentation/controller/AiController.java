package com.luckylogistics.ai.presentation.controller;

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

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptReadResult;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.dto.StatusUpdateCommand;
import com.luckylogistics.ai.application.service.AiService;
import com.luckylogistics.ai.presentation.ApiResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedRequest;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptDetailResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptStatusUpdateRequest;
import com.luckylogistics.ai.presentation.dto.AiPromptSummaryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-prompts")
@RequiredArgsConstructor
public class AiController {

	private final AiService aiService;

	@PostMapping
	public ResponseEntity<ApiResponse<AiPromptCreatedResponse>> createAiPrompt(
		@Valid @RequestBody AiPromptCreatedRequest requestDto
	) {
		AiPromptCreatedCommand command = AiPromptCreatedCommand.from(requestDto);
		AiPromptResult result = aiService.createAiPrompt(command);
		AiPromptCreatedResponse responseDto = AiPromptCreatedResponse.from(result);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
	}

	// TODO: 페이징 및 검색 구현
	@GetMapping
	public ResponseEntity<ApiResponse<List<AiPromptSummaryResponse>>> getAllPrompts() {
		List<AiPromptReadResult> resultList = aiService.getAllPrompts();
		List<AiPromptSummaryResponse> responseDtoList = resultList.stream().map(AiPromptSummaryResponse::from).toList();

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDtoList));
	}

	@GetMapping("/{aiPromptId}")
	public ResponseEntity<ApiResponse<AiPromptDetailResponse>> getPrompt(@PathVariable UUID aiPromptId) {
		AiPromptReadResult result = aiService.getPrompt(aiPromptId);
		AiPromptDetailResponse responseDto = AiPromptDetailResponse.from(result);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDto));
	}

	@PatchMapping("/{aiPromptId}/status")
	public ResponseEntity<ApiResponse<Void>> updateStatus(
		@PathVariable UUID aiPromptId, @Valid @RequestBody AiPromptStatusUpdateRequest requestDto
	) {
		StatusUpdateCommand command = StatusUpdateCommand.from(requestDto);
		aiService.updateStatus(aiPromptId, command);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
	}

	@DeleteMapping("/{aiPromptId}")
	public ResponseEntity<ApiResponse<Void>> deletePrompt(@PathVariable UUID aiPromptId) {
		aiService.deletePrompt(aiPromptId);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
	}

}

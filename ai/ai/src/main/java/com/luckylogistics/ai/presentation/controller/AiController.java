package com.luckylogistics.ai.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.service.AiService;
import com.luckylogistics.ai.presentation.ApiResponse;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedRequest;
import com.luckylogistics.ai.presentation.dto.AiPromptCreatedResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ai-prompts")
@RequiredArgsConstructor
public class AiController {

	private final AiService aiService;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> createAiPrompt(
		@Valid @RequestBody AiPromptCreatedRequest requestDto
	) {
		AiPromptCreatedCommand command = AiPromptCreatedCommand.from(requestDto);
		AiPromptResult result = aiService.createAiPrompt(command);
		AiPromptCreatedResponse responseDto = AiPromptCreatedResponse.from(result);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
	}

}

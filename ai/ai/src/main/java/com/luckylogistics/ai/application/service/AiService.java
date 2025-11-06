package com.luckylogistics.ai.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.GeminiPromptResult;
import com.luckylogistics.ai.application.external.GeminiClient;
import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.repository.AiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

	private final AiRepository aiRepository;
	private final GeminiClient geminiClient;
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	@Transactional
	public GeminiPromptResult createAiPrompt(AiPromptCreatedCommand command) {
		AiPrompt aiPrompt = AiPrompt.builder()
			.requestContent(toJson(command))
			.build();
		aiRepository.save(aiPrompt);
		log.info("AI 프롬프트 요청 상태 - {}", aiPrompt.getStatus().getDescription());

		GeminiPromptResult result = geminiClient.generatePrompt(command);
		if (result.error() == null) {
			aiPrompt.updateStatus("SUCCESS");
		} else {
			aiPrompt.updateStatus("RETRY");
		}
		log.info("AI 프롬프트 요청 상태 - {}", aiPrompt.getStatus().getDescription());

		return result;
	}

	private String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 형식으로 변환할 수 없습니다.", e);
		}
	}

}

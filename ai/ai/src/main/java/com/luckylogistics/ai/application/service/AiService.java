package com.luckylogistics.ai.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptReadResult;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.dto.StatusUpdateCommand;
import com.luckylogistics.ai.application.external.AiPromptGenerator;
import com.luckylogistics.ai.common.exception.BusinessException;
import com.luckylogistics.ai.common.exception.ErrorCode;
import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.repository.AiRepository;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

	private final AiRepository aiRepository;
	private final AiPromptGenerator aiPromptGenerator;
	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	private final int MAX_RETRY = 3;
	private final long SLEEP_MILLIS = 5000L;

	// @Transactional
	// TODO: 별도의 서비스 클래스를 통해 트랜잭셔널 분리
	public AiPromptResult createAiPrompt(AiPromptCreatedCommand command) {
		AiPrompt aiPrompt = AiPrompt.builder()
			.requestContent(toJson(command))
			.build();
		aiRepository.save(aiPrompt);
		log.info("AI 프롬프트 요청 상태 - {}", aiPrompt.getStatus().getDescription());

		return generateAiPrompt(aiPrompt, command);
	}

	@Transactional(readOnly = true)
	public List<AiPromptReadResult> getAllPrompts() {
		return aiRepository.findAllByDeletedAtIsNull().stream().map(AiPromptReadResult::from).toList();
	}

	@Transactional(readOnly = true)
	public AiPromptReadResult getPrompt(UUID aiPromptId) {
		return aiRepository.findByAiPromptIdAndDeletedAtIsNull(aiPromptId)
			.map(AiPromptReadResult::from)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
	}

	@Transactional
	public void updateStatus(UUID aiPromptId, StatusUpdateCommand command) {
		AiPrompt aiPrompt = aiRepository.findByAiPromptIdAndDeletedAtIsNull(aiPromptId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
		aiPrompt.updateStatus(command.status());
	}

	@Transactional
	public void deletePrompt(UUID aiPromptId) {
		AiPrompt aiPrompt = aiRepository.findByAiPromptIdAndDeletedAtIsNull(aiPromptId)
			.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
		aiPrompt.softDelete(1L);
	}

	private String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private AiPromptResult generateAiPrompt(AiPrompt aiPrompt, AiPromptCreatedCommand command) {
		for (int retry = 1; retry <= MAX_RETRY; retry++) {
			try {
				AiPromptResult result = aiPromptGenerator.generatePrompt(command);

				aiPrompt.updateResponseContent(result.responseContent());
				aiPrompt.updateStatus("SUCCESS");
				return result;
			} catch (BusinessException e) {
				if (isRetryable(e) && retry < MAX_RETRY) {
					aiPrompt.updateStatus("RETRY");
					sleep();
					continue;
				}

				aiPrompt.updateStatus("FAILED");
				throw e;
			} catch (Exception e) {
				aiPrompt.updateStatus("FAILED");
				throw e;
			} finally {
				aiRepository.save(aiPrompt);
				log.info("AI 프롬프트 요청 상태 - {}", aiPrompt.getStatus().getDescription());
			}
		}

		return null;
	}

	private boolean isRetryable(BusinessException e) {
		return e.getErrorCode() == ErrorCode.GEMINI_API_TIMEOUT || e.getErrorCode() == ErrorCode.GEMINI_API_SERVICE_UNAVAILABLE;
	}

	private void sleep() {
		try {
			Thread.sleep(SLEEP_MILLIS);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
	}

}

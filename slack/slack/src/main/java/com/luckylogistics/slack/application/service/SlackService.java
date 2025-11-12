package com.luckylogistics.slack.application.service;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;
import com.luckylogistics.slack.application.dto.EmailCheckCommand;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.luckylogistics.slack.application.dto.SlackEmailCheckResult;
import com.luckylogistics.slack.application.dto.SlackMessageResult;
import com.luckylogistics.slack.application.dto.StatusUpdateCommand;
import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.application.external.SlackClient;
import com.luckylogistics.slack.common.exception.BusinessException;
import com.luckylogistics.slack.common.exception.ErrorCode;
import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.repository.SlackRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

	private final SlackRepository slackRepository;
	private final SlackClient slackClient;
	private final AiServiceClient aiServiceClient;

	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	// @Transactional
	public void sendMessage(OrderCreatedResult result, String receiverEmail, LocalTime startTime, LocalTime endTIme) {
		SlackMessage slackMessage = SlackMessage.builder().receiverEmail(receiverEmail).content(toJson(result)).build();
		slackRepository.save(slackMessage);
		log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());

		try {
			Instant aiPrompt = generateAiPrompt(result, startTime, endTIme);
			slackClient.sendMessage(result, receiverEmail, aiPrompt);
			slackMessage.updateStatus("SUCCESS");
		} catch (Exception e) {
			slackMessage.updateStatus("RETRY");
			throw e;
		} finally {
			slackRepository.save(slackMessage);
			log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());
		}
	}

	@Transactional(readOnly = true)
	public List<SlackMessageResult> getAllMessages() {
		return slackRepository.findAll().stream().map(SlackMessageResult::from).toList();
	}

	@Transactional(readOnly = true)
	public SlackMessageResult getMessage(UUID slackMessageId) {
		return slackRepository.findById(slackMessageId)
			.map(SlackMessageResult::from)
			.orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public SlackEmailCheckResult checkInWorkspace(EmailCheckCommand command) {
		return SlackEmailCheckResult.builder().exists(slackClient.existsByEmail(command.email())).build();
	}

	@Transactional
	public void updateStatus(UUID slackMessageId, StatusUpdateCommand command) {
		SlackMessage slackMessage = slackRepository.findById(slackMessageId)
			.orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));
		slackMessage.updateStatus(command.status());
	}

	// TODO: softDelete에 로그인한 사용자의 userId를 넘겨주기
	@Transactional
	public void deleteMessage(UUID slackMessageId) {
		SlackMessage slackMessage = slackRepository.findById(slackMessageId)
			.orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));
		slackMessage.softDelete(1L);
	}

	private String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private Instant generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTIme) {
		try {
			AiPromptCreatedResult aiResult = aiServiceClient.generateAiPrompt(result, startTime, endTIme);
			return aiResult.responseContent();
		} catch (Exception e) {
			log.warn(e.getMessage());
			throw e;
		}
	}

}

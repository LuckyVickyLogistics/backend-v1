package com.luckylogistics.slack.application.service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckylogistics.slack.application.command.EmailCheckCommand;
import com.luckylogistics.slack.application.command.StatusUpdateCommand;
import com.luckylogistics.slack.application.event.SlackKafkaEventPublisher;
import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.application.external.SlackClient;
import com.luckylogistics.slack.application.result.AiPromptCreatedResult;
import com.luckylogistics.slack.application.result.OrderCreatedResult;
import com.luckylogistics.slack.application.result.SlackEmailCheckResult;
import com.luckylogistics.slack.application.result.SlackMessageResult;
import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.repository.SlackRepository;
import com.luckylogistics.slack.domain.vo.Status;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

	private final SlackRepository slackRepository;
	private final SlackKafkaEventPublisher slackEventPublisher;
	private final SlackClient slackClient;
	private final AiServiceClient aiServiceClient;

	private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	// TODO: 실제로는 application 계층에서 infrastructure 계층을 참조하면 안됨
	public void publish(OrderCreatedEvent requestDto) {
		slackEventPublisher.publish(requestDto);
	}

	@Transactional
	public void sendMessage(OrderCreatedResult result, String receiverEmail, LocalTime startTime, LocalTime endTIme) {
		SlackMessage slackMessage = SlackMessage.builder().receiverEmail(receiverEmail).content(toJson(result)).build();
		slackRepository.save(slackMessage);
		log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());

		String aiPrompt = generateAiPrompt(result, startTime, endTIme);
		if (sendMessage(result, receiverEmail, aiPrompt)) {
			slackMessage.updateStatus("SUCCESS");
		} else {
			slackMessage.updateStatus("RETRY");
		}
		log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());
	}

	@Transactional(readOnly = true)
	public List<SlackMessageResult> getAllMessages() {
		return slackRepository.findAll().stream().map(SlackMessageResult::from).toList();
	}

	@Transactional(readOnly = true)
	public SlackMessageResult getMessage(UUID slackMessageId) {
		return slackRepository.findById(slackMessageId)
			.map(SlackMessageResult::from)
			.orElseThrow(() -> new IllegalArgumentException("일치하는 슬랙 메시지를 찾을 수 없습니다."));
	}

	@Transactional(readOnly = true)
	public SlackEmailCheckResult checkInWorkspace(EmailCheckCommand command) {
		return SlackEmailCheckResult.builder().exists(slackClient.existsByEmail(command.email())).build();
	}

	@Transactional
	public void updateStatus(UUID slackMessageId, StatusUpdateCommand command) {
		SlackMessage slackMessage = slackRepository.findById(slackMessageId)
			.orElseThrow(() -> new IllegalArgumentException("일치하는 슬랙 메시지를 찾을 수 없습니다."));
		slackMessage.updateStatus(command.status());
	}

	// TODO: softDelete에 로그인한 사용자의 userId를 넘겨주기
	@Transactional
	public void deleteMessage(UUID slackMessageId) {
		SlackMessage slackMessage = slackRepository.findById(slackMessageId)
			.orElseThrow(() -> new IllegalArgumentException("일치하는 슬랙 메시지를 찾을 수 없습니다."));
		slackMessage.softDelete(1L);
	}

	private String generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTIme) {
		try {
			AiPromptCreatedResult aiResult = aiServiceClient.generateAiPrompt(result, startTime, endTIme);
			return aiResult.responseContent();
		} catch (Exception e) {
			log.warn("AI 프롬프트 생성 실패");
			throw new RuntimeException("AI 프롬프트 생성 실패");
		}
	}

	private boolean sendMessage(OrderCreatedResult orderResult, String receiverEmail, String aiPrompt) {
		try {
			slackClient.sendMessage(orderResult, receiverEmail, aiPrompt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 형식으로 변환할 수 없습니다.", e);
		}
	}

}

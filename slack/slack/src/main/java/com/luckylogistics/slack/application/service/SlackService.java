package com.luckylogistics.slack.application.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.luckylogistics.slack.application.result.AiPromptCreatedResult;
import com.luckylogistics.slack.application.result.OrderCreatedResult;
import com.luckylogistics.slack.application.event.SlackKafkaEventPublisher;
import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.vo.Status;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;
import com.luckylogistics.slack.application.external.SlackClient;
import com.luckylogistics.slack.domain.repository.SlackRepository;

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

	private final ObjectMapper mapper = new ObjectMapper()
		.registerModule(new JavaTimeModule())
		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	// TODO: 실제로는 application 계층에서 infrastructure 계층을 참조하면 안됨
	public void publish(OrderCreatedEvent requestDto) {
		slackEventPublisher.publish(requestDto);
	}

	@Transactional
	public void sendMessage(OrderCreatedResult result, String receiverEmail, LocalTime startTime, LocalTime endTIme) {
		SlackMessage slackMessage = SlackMessage.builder()
			.receiverEmail(receiverEmail)
			.content(toJson(result))
			.build();
		slackRepository.save(slackMessage);
		log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());

		String aiPrompt = generateAiPrompt(result, startTime, endTIme);
		if (sendMessage(result, receiverEmail, aiPrompt)) {
			slackMessage.updateStatus(Status.SUCCESS);
		} else {
			slackMessage.updateStatus(Status.RETRY);
		}
		log.info("슬랙 메시지 발송 상태 - {}", slackMessage.getStatus().getDescription());
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

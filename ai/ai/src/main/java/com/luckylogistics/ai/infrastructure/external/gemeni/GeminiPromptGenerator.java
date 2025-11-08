package com.luckylogistics.ai.infrastructure.external.gemeni;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.external.AiPromptGenerator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeminiPromptGenerator implements AiPromptGenerator {

	private final Client client;
	private final String model;
	private final ObjectMapper mapper;

	public GeminiPromptGenerator(@Value("${gemini.api.key}") String key, @Value("${gemini.api.model}") String model) {
		this.client = Client.builder().apiKey(key).build();
		this.model = model;
		this.mapper = new ObjectMapper();
	}

	// TODO: 예외 처리 세분화
	@Override
	public AiPromptResult generatePrompt(AiPromptCreatedCommand command) {
		try {
			GenerateContentConfig config = GenerateContentConfig.builder()
				.responseMimeType("application/json")
				.responseSchema(Schema.builder()
					.type(Type.Known.OBJECT)
					.properties(Map.of(
						"responseContent", Schema.builder()
							.type(Type.Known.STRING)
							.format("date-time")
							.build()
					))
					.required("responseContent")
					.build())
				.build();
			GenerateContentResponse response = client.models.generateContent(model, createContentList(command), config);

			return AiPromptResult.from(
				convertResponseToInstant(response, command.deliveryManagerStartTime(), command.deliveryManagerEndTime())
			);
		} catch (Exception e) {
			throw new RuntimeException("Gemini API 호출에 실패했습니다.");
		}
	}

	private List<Content> createContentList(AiPromptCreatedCommand command) {
		return List.of(
			createContent("아래 정보를 바탕으로 납기일에 맞춰 도착하기 위한 최종 발송 시한 계산"),
			createContent("현재날짜 : " + LocalDateTime.now()),
			createContent("상품명 : " + command.productName()),
			createContent("수량 : " + command.quantity()),
			createContent("요청사항 : " + command.request()),
			createContent("발송지 : " + command.startPoint()),
			createContent("경유지 : " + command.waypoints()),
			createContent("도착지 : " + command.endPoint()),
			createContent("배송담당자근무시작시간 : " + command.deliveryManagerStartTime()),
			createContent("배송담당자근무마감시간 : " + command.deliveryManagerEndTime()),
			createContent("만약 요청사항에 언제까지 보내달라는 내용이 없다면 3일 후로 답변"),
			createContent("배송 담당자 근무 시간 내에서만 최종 발송 시한을 계산하여 답변")
		);
	}

	private Content createContent(String text) {
		return Content.builder()
			.role("user")
			.parts(Part.builder().text(text).build())
			.build();
	}

	private Instant convertResponseToInstant(GenerateContentResponse response, LocalTime startTime, LocalTime endTime) {
		try {
			String responseContent = mapper.readTree(response.text()).get("responseContent").asText();
			Instant parsedResponseContent = Instant.parse(responseContent);
			return adjustToWorkingTime(parsedResponseContent, startTime, endTime);
		} catch (JsonProcessingException | NullPointerException | DateTimeParseException e) {
			throw new RuntimeException("Gemini API 호출에서 예상한 값을 받을 수 없습니다.");
		}
	}

	private Instant adjustToWorkingTime(Instant responseContent, LocalTime startTime, LocalTime endTime) {
		ZonedDateTime zdt = responseContent.atZone(ZoneId.of("Asia/Seoul"));
		LocalTime time = zdt.toLocalTime();

		if (time.isBefore(startTime)) {
			return replaceTime(zdt, startTime);
		} else if (time.isAfter(endTime)) {
			return replaceTime(zdt, endTime);
		} else {
			return responseContent;
		}
	}

	private Instant replaceTime(ZonedDateTime zdt, LocalTime time) {
		return zdt.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0).withNano(0).toInstant();
	}

}

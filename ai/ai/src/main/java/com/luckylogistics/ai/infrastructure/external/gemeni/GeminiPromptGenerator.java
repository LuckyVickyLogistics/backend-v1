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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.errors.ClientException;
import com.google.genai.errors.GenAiIOException;
import com.google.genai.errors.ServerException;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.HttpOptions;
import com.google.genai.types.Part;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptResult;
import com.luckylogistics.ai.application.exception.AiException;
import com.luckylogistics.ai.application.external.AiPromptGenerator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GeminiPromptGenerator implements AiPromptGenerator {

	private final Client client;
	private final String model;
	private final ObjectMapper mapper;

	public GeminiPromptGenerator(@Value("${gemini.api.key}") String key, @Value("${gemini.api.model}") String model) {
		this.client = Client.builder().httpOptions(HttpOptions.builder().timeout(10000).build()).apiKey(key).build();
		this.model = model;
		this.mapper = new ObjectMapper();
	}

	@Override
	public AiPromptResult generatePrompt(AiPromptCreatedCommand command) {
		GenerateContentConfig config = generateContentConfig();
		try {
			GenerateContentResponse response = client.models.generateContent(model, createContentList(command), config);
			return AiPromptResult.from(
				convertResponseToInstant(response, command.deliveryManagerStartTime(), command.deliveryManagerEndTime())
			);
		} catch (GenAiIOException e) {
			throw new AiException(HttpStatus.GATEWAY_TIMEOUT, "Gemini API 요청이 타임아웃되었습니다.", e);
		} catch (ClientException e) {
			switch (e.code()) {
				case 400: throw new AiException(HttpStatus.BAD_REQUEST, "Gemini API 요청이 잘못되었습니다.", e);
				case 403: throw new AiException(HttpStatus.FORBIDDEN, "Gemini API 요청에 권한이 없습니다.", e);
				case 404: throw new AiException(HttpStatus.NOT_FOUND, "Gemini API 요청한 리소스를 찾을 수 없습니다.", e);
				case 429: throw new AiException(HttpStatus.TOO_MANY_REQUESTS, "Gemini API 요청 제한 횟수를 초과했습니다.", e);
				default: throw new AiException(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 서비스에 알 수 없는 오류가 발생했습니다.", e);
			}
		} catch (ServerException e) {
			switch (e.code()) {
				case 500: throw new AiException(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 서비스 내부에 오류가 발생했습니다.", e);
				case 503: throw new AiException(HttpStatus.SERVICE_UNAVAILABLE, "Gemini API 서비스를 일시적으로 호출할 수 없습니다.", e);
				default: throw new AiException(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 서비스에 알 수 없는 오류가 발생했습니다.", e);
			}
		}
	}

	private GenerateContentConfig generateContentConfig() {
		return GenerateContentConfig.builder()
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
			throw new AiException(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 응답 파싱에 실패했습니다.", e);
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

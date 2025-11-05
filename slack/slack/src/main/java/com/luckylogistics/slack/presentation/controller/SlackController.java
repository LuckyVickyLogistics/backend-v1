package com.luckylogistics.slack.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.slack.application.service.SlackService;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;
import com.luckylogistics.slack.presentation.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/slack-messages")
@RequiredArgsConstructor
public class SlackController {

	private final SlackService slackService;

	// TODO: 테스트용 이벤트 생성 메서드로, 주문 서비스 개발 완료 후 삭제
	@PostMapping("/publish")
	public ResponseEntity<ApiResponse<Void>> publish(@RequestBody OrderCreatedEvent requestDto) {
		slackService.publish(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
	}

}

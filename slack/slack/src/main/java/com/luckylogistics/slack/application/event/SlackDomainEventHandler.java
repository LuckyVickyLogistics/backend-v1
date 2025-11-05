package com.luckylogistics.slack.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.luckylogistics.slack.application.service.SlackService;
import com.luckylogistics.slack.domain.event.SlackMessagePendingEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackEventHandler {

	private final SlackService slackService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleSlackSlackMessagePending(SlackMessagePendingEvent event) {
		log.info("슬랙 메세지 발송 상태 - {}", event.status());
	}

	public void test() {
		slackService.test();
	}

}

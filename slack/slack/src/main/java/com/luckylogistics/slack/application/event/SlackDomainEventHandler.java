package com.luckylogistics.slack.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.luckylogistics.slack.domain.event.SlackMessageSavedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SlackDomainEventHandler {

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleSlackMessageSaved(SlackMessageSavedEvent event) {
	}

}

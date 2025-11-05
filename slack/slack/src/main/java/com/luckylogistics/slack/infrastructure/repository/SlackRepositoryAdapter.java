package com.luckylogistics.slack.infrastructure.repository;

import org.springframework.stereotype.Component;

import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.repository.SlackRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackRepositoryAdapter implements SlackRepository {

	private final JpaSlackRepository jpaSlackRepository;

	@Override
	public SlackMessage save(SlackMessage slackMessage) {
		return jpaSlackRepository.save(slackMessage);
	}

}

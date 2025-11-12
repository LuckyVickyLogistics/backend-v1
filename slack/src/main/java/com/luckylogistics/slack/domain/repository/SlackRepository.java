package com.luckylogistics.slack.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.luckylogistics.slack.domain.entity.SlackMessage;

public interface SlackRepository {

	void save(SlackMessage slackMessage);

	List<SlackMessage> findAll();

	Optional<SlackMessage> findById(UUID slackMessageId);

}

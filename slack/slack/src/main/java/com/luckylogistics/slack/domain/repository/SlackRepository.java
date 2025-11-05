package com.luckylogistics.slack.domain.repository;

import com.luckylogistics.slack.domain.entity.SlackMessage;

public interface SlackRepository {

	SlackMessage save(SlackMessage slackMessage);

}

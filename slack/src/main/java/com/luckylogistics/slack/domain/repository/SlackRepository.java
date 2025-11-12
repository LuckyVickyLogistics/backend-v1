package com.luckylogistics.slack.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.vo.Status;

public interface SlackRepository {

	void save(SlackMessage slackMessage);

	List<SlackMessage> findAll();

	Optional<SlackMessage> findById(UUID slackMessageId);

	Page<SlackMessage> findAllByReceiverEmailAndStatusAndDeletedAtIsNull(String receiverEmail, Status status, Pageable pageable);
}

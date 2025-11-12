package com.luckylogistics.slack.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.repository.SlackRepository;
import com.luckylogistics.slack.domain.vo.Status;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackRepositoryAdapter implements SlackRepository {

	private final JpaSlackRepository jpaSlackRepository;

	@Override
	public void save(SlackMessage slackMessage) {
		jpaSlackRepository.save(slackMessage);
	}

	@Override
	public List<SlackMessage> findAll() {
		return jpaSlackRepository.findAll();
	}

	@Override
	public Optional<SlackMessage> findById(UUID slackMessageId) {
		return jpaSlackRepository.findById(slackMessageId);
	}

	@Override
	public Page<SlackMessage> findAllByReceiverEmailAndStatusAndDeletedAtIsNull(
		String receiverEmail, Status status, Pageable pageable
	) {
		return jpaSlackRepository.findAllByReceiverEmailAndStatusAndDeletedAtIsNull(receiverEmail, status, pageable);
	}
}

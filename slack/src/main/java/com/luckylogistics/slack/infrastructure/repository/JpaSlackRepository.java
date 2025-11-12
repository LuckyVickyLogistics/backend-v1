package com.luckylogistics.slack.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.vo.Status;

public interface JpaSlackRepository extends JpaRepository<SlackMessage, UUID> {

	@Query("""
			SELECT s
			FROM SlackMessage s
			WHERE s.deletedAt IS NULL
		""")
	List<SlackMessage> findAll();

	@Query("""
			SELECT s
			FROM SlackMessage s
			WHERE s.slackMessageId = :slackMessageId AND s.deletedAt IS NULL
		""")
	Optional<SlackMessage> findById(@Param("slackMessageId") UUID slackMessageId);

	@Query("""
		SELECT s
		FROM SlackMessage s
		WHERE (:receiverEmail IS NULL OR s.receiverEmail = :receiverEmail)
			AND (:status IS NULL OR s.status = :status)
			AND s.deletedAt IS NULL
	""")
	Page<SlackMessage> findAllByReceiverEmailAndStatusAndDeletedAtIsNull(
		@Param("receiverEmail") String receiverEmail, @Param("status") Status status, Pageable pageable
	);
}

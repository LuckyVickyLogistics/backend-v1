package com.luckylogistics.slack.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luckylogistics.slack.domain.entity.SlackMessage;

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

}

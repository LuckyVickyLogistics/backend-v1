package com.luckylogistics.slack.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luckylogistics.slack.domain.entity.SlackMessage;

public interface JpaSlackRepository extends JpaRepository<SlackMessage, UUID> {
}

package com.luckylogistics.ai.infrastructure.repository;

import org.springframework.stereotype.Component;

import com.luckylogistics.ai.domain.repository.AiRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AiRepositoryAdapter implements AiRepository {

	private final JpaAiRepository jpaAiRepository;

}

package com.luckylogistics.ai.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.repository.AiRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AiRepositoryAdapter implements AiRepository {

	private final JpaAiRepository jpaAiRepository;

	@Override
	public void save(AiPrompt aiPrompt) {
		jpaAiRepository.save(aiPrompt);
	}

	@Override
	public List<AiPrompt> findAllByDeletedAtIsNull() {
		return jpaAiRepository.findAllByDeletedAtIsNull();
	}

	@Override
	public Optional<AiPrompt> findByAiPromptIdAndDeletedAtIsNull(UUID aiPromptId) {
		return jpaAiRepository.findByAiPromptIdAndDeletedAtIsNull(aiPromptId);
	}

}

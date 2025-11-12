package com.luckylogistics.ai.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.luckylogistics.ai.domain.entity.AiPrompt;

public interface AiRepository {

	void save(AiPrompt aiPrompt);

	List<AiPrompt> findAllByDeletedAtIsNull();

	Optional<AiPrompt> findByAiPromptIdAndDeletedAtIsNull(UUID aiPromptId);

}

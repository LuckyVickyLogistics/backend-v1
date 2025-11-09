package com.luckylogistics.ai.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luckylogistics.ai.domain.entity.AiPrompt;

public interface JpaAiRepository extends JpaRepository<AiPrompt, UUID> {

	List<AiPrompt> findAllByDeletedAtIsNull();

	Optional<AiPrompt> findByAiPromptIdAndDeletedAtIsNull(UUID aiPromptId);

}

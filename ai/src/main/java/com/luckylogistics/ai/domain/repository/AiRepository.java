package com.luckylogistics.ai.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.vo.Status;

public interface AiRepository {

	void save(AiPrompt aiPrompt);

	List<AiPrompt> findAllByDeletedAtIsNull();

	Optional<AiPrompt> findByAiPromptIdAndDeletedAtIsNull(UUID aiPromptId);

	Page<AiPrompt> findAllByStatusAndDeletedAtIsNull(Status status, Pageable pageable);
}


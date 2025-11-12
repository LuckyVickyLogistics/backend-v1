package com.luckylogistics.ai.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.vo.Status;

public interface JpaAiRepository extends JpaRepository<AiPrompt, UUID> {

	List<AiPrompt> findAllByDeletedAtIsNull();

	Optional<AiPrompt> findByAiPromptIdAndDeletedAtIsNull(UUID aiPromptId);

	@Query("""
		SELECT a
		FROM AiPrompt a
		WHERE (:status IS NULL OR a.status = :status)
			AND a.deletedAt IS NULL
	""")
	Page<AiPrompt> findAllByStatusAndDeletedAtIsNull(
		@Param("status") Status status, Pageable pageable
	);
}

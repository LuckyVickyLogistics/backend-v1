package com.luckylogistics.ai.domain.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
// TODO: AuditorAware 구현
public class BaseEntity<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {

	@CreatedDate
	@Column(updatable = false)
	private Instant createdAt;

	private Long createdBy;

	@LastModifiedDate
	private Instant updatedAt;

	private Long updatedBy;

	private Instant deletedAt;

	private Long deletedBy;

	public void softDelete(Long userId) {
		if (deletedAt == null) {
			deletedAt = Instant.now();
			deletedBy = userId;
		}
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

}
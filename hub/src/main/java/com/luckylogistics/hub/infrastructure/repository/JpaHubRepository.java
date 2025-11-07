package com.luckylogistics.hub.infrastructure.repository;

import java.util.Optional;

import com.luckylogistics.hub.infrastructure.entity.HubJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHubRepository extends JpaRepository<HubJpaEntity, String> {
    Optional<HubJpaEntity> findByAddress(String address);
}
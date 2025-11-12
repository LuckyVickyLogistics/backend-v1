package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.HubManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubManagerJpaRepository extends JpaRepository<HubManager, UUID> {

    List<HubManager> findByIsDeletedIsFalse();

    Optional<HubManager> findByHubId(UUID hubId);
    Optional<HubManager> findByHubIdAndIsDeletedIsFalse(UUID hubId);

    Optional<HubManager> findByUserIdAndIsDeletedIsFalse(Long userId);

    Optional<HubManager> findByHubManagerId(UUID hubMangerId);
    Optional<HubManager> findByHubManagerIdAndIsDeletedIsFalse(UUID hubManagerId);
}

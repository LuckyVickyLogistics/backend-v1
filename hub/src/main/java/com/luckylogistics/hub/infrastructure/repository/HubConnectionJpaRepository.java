package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.HubConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubConnectionJpaRepository extends JpaRepository<HubConnection, UUID> {
    List<HubConnection> findByFromHub_HubId(UUID fromHubId);
    Optional<HubConnection> findByFromHub_HubIdAndToHub_HubId(UUID fromHubId, UUID toHubId);
}

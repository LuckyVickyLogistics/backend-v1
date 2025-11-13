package com.luckylogistics.hub.domain.repository;

import com.luckylogistics.hub.domain.model.HubConnection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubConnectionRepository {
    List<HubConnection> findAll();
    List<HubConnection> findByFromId(UUID fromHubId);
    Optional<HubConnection> findByFromIdAndToId(UUID fromHubId, UUID toHubId);
}

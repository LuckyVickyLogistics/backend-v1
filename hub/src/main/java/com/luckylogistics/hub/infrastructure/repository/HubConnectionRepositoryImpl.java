package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.HubConnection;
import com.luckylogistics.hub.domain.repository.HubConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubConnectionRepositoryImpl implements HubConnectionRepository {

    private final HubConnectionJpaRepository repository;

    @Override
    public List<HubConnection> findAll() {
        return repository.findAll();
    }

    @Override
    public List<HubConnection> findByFromId(UUID fromHubId) {
        return repository.findByFromHub_HubId(fromHubId);
    }

    @Override
    public Optional<HubConnection> findByFromIdAndToId(UUID fromHubId, UUID toHubId) {
        return repository.findByFromHub_HubIdAndToHub_HubId(fromHubId, toHubId);
    }
}

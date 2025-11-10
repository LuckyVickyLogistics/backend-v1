package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final HubJpaRepository hubJpaRepository;

    @Override
    public Hub save(Hub hub) {
        return hubJpaRepository.save(hub);
    }

    @Override
    public Optional<Hub> findById(UUID hubId) {
        return hubJpaRepository.findById(hubId);
    }

    @Override
    public List<Hub> findAll() {
        return hubJpaRepository.findAll();
    }

    @Override
    public Optional<Hub> findActiveById(UUID hubId) {
        return hubJpaRepository.findByHubIdAndIsDeletedFalse(hubId);
    }

    @Override
    public List<Hub> findAllActive() {
        return hubJpaRepository.findAllByIsDeletedFalse();
    }
}

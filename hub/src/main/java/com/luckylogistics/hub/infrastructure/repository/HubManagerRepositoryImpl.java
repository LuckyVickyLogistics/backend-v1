package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.model.HubManager;
import com.luckylogistics.hub.domain.repository.HubManagerRepository;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubManagerRepositoryImpl implements HubManagerRepository {

    private final HubManagerJpaRepository jpaRepository;

    @Override
    public HubManager save(HubManager hubManager) {
        return jpaRepository.save(hubManager);
    }

    @Override
    public Optional<HubManager> findByManagerId(UUID managerId) {
        return jpaRepository.findByHubManagerId(managerId);
    }

    @Override
    public Optional<HubManager> findActiveByManagerId(UUID managerId) {
        return jpaRepository.findByHubManagerIdAndIsDeletedIsFalse(managerId);
    }

    @Override
    public Optional<HubManager> findActiveByUserId(Long userId) {
        return jpaRepository.findByUserIdAndIsDeletedIsFalse(userId);
    }

    @Override
    public Optional<HubManager> findByHubId(UUID hubId) {
        return jpaRepository.findByHubId(hubId);
    }

    @Override
    public Optional<HubManager> findActiveByHubId(UUID hubId) {
        return jpaRepository.findByHubIdAndIsDeletedIsFalse(hubId);
    }

    @Override
    public List<HubManager> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<HubManager> findAllActive() {
        return jpaRepository.findByIsDeletedIsFalse();
    }

}

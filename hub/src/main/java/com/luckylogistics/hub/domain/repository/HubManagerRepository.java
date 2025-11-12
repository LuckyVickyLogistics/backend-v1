package com.luckylogistics.hub.domain.repository;

import com.luckylogistics.hub.domain.model.HubManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubManagerRepository {

    HubManager save(HubManager hubManager);

    Optional<HubManager> findByManagerId(UUID managerId);
    Optional<HubManager> findActiveByManagerId(UUID managerId);

    Optional<HubManager> findActiveByUserId(Long userId);

    // soft delete 된 것 제외하고 조회하고 싶으면 이런 시그니처도 사용
    Optional<HubManager> findByHubId(UUID hubId);
    Optional<HubManager> findActiveByHubId(UUID hubId);

    List<HubManager> findAll();
    List<HubManager> findAllActive();
}

package com.luckylogistics.hub.domain.repository;

import com.luckylogistics.hub.domain.model.Hub;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubRepository {

    Hub save(Hub hub);

    Optional<Hub> findById(UUID hubId);

    List<Hub> findAll();

    // soft delete 된 것 제외하고 조회하고 싶으면 이런 시그니처도 사용
    Optional<Hub> findActiveById(UUID hubId);

    List<Hub> findAllActive();
}

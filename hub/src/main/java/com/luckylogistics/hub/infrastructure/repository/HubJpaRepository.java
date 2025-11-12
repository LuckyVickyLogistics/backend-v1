package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HubJpaRepository extends JpaRepository<Hub, UUID> {

    List<Hub> findAllByIsDeletedFalse();

    Optional<Hub> findByHubIdAndIsDeletedFalse(UUID hubId);
}

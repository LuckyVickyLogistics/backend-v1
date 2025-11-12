package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryManagerRepository extends JpaRepository<DeliveryManager, Long> {

    Optional<DeliveryManager> findByDeliveryManagerIdAndDeletedAtIsNull(Long id);

    boolean existsByDeliveryManagerIdAndDeletedAtIsNull(Long id);

    @Query("""
        SELECT dm
        FROM DeliveryManager dm
        WHERE (:type IS NULL OR dm.type = :type)
          AND (:hubId IS NULL OR dm.hubId = :hubId)
          AND dm.deletedAt IS NULL
    """)
    Page<DeliveryManager> findByTypeAndHubId(
            @Param("type") DeliveryManagerType type,
            @Param("hubId") UUID hubId,
            Pageable pageable
    );

    @Query("""
        SELECT MAX(dm.deliverySequence)
        FROM DeliveryManager dm
        WHERE dm.type = :type AND dm.deletedAt IS NULL
    """)
    Optional<Integer> findMaxSequenceByType(@Param("type") DeliveryManagerType type);

    @Query("""
        SELECT MAX(dm.deliverySequence)
        FROM DeliveryManager dm
        WHERE dm.type = :type
          AND dm.hubId = :hubId
          AND dm.deletedAt IS NULL
    """)
    Optional<Integer> findMaxSequenceByTypeAndHubId(
            @Param("type") DeliveryManagerType type,
            @Param("hubId") UUID hubId
    );

    // 허브별 업체 담당자 순서
    @Query("""
        SELECT dm FROM DeliveryManager dm
        WHERE dm.hubId = :hubId
          AND dm.type = 'COMPANY_DELIVERY'
          AND dm.deletedAt IS NULL
        ORDER BY dm.deliverySequence
    """)
    List<DeliveryManager> findCompanyDeliveryManagersByHubId(@Param("hubId") UUID hubId);

    @Query("""
        SELECT dm FROM DeliveryManager dm
        WHERE dm.type = 'HUB_DELIVERY'
          AND dm.deletedAt IS NULL
        ORDER BY dm.deliverySequence
    """)
    List<DeliveryManager> findHubDeliveryManagers();
}
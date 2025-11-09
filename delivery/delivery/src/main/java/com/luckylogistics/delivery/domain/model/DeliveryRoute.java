package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_delivery_route")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DeliveryRoute extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_route_id", nullable = false)
    private UUID deliveryRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "departure_hub_id", nullable = false)
    private UUID departureHubId;

    @Column(name = "arrival_hub_id", nullable = false)
    private UUID arrivalHubId;

    @Column(name = "estimated_distance", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedDistance;

    @Column(name = "estimated_duration", nullable = false)
    private Integer estimatedDuration;

    @Column(name = "actual_distance", precision = 10, scale = 2)
    private BigDecimal actualDistance;

    @Column(name = "actual_duration")
    private Integer actualDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryRouteStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hub_delivery_manager_id", nullable = false)
    private DeliveryManager hubDeliveryManager;

    /**
     * 배송 경로 생성
     */
    public static DeliveryRoute create(
            Delivery delivery,
            Integer sequence,
            UUID departureHubId,
            UUID arrivalHubId,
            BigDecimal estimatedDistance,
            Integer estimatedDuration,
            DeliveryManager deliveryManager
    ) {
        validateDelivery(delivery);
        validateSequence(sequence);
        validateHubIds(departureHubId, arrivalHubId);
        validateEstimatedValues(estimatedDistance, estimatedDuration);
        validateDeliveryManager(deliveryManager);

        return DeliveryRoute.builder()
                .delivery(delivery)
                .sequence(sequence)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .estimatedDistance(estimatedDistance)
                .estimatedDuration(estimatedDuration)
                .status(DeliveryRouteStatus.HUB_WAITING)
                .hubDeliveryManager(deliveryManager)
                .build();
    }

    private static void validateDelivery(Delivery delivery) {
        if (delivery == null) {
            throw new IllegalArgumentException("배송 정보는 필수입니다");
        }
    }

    private static void validateSequence(Integer sequence) {
        if (sequence == null || sequence < 1) {
            throw new IllegalArgumentException("경로 순서는 1 이상이어야 합니다");
        }
    }

    private static void validateHubIds(UUID departureHubId, UUID arrivalHubId) {
        if (departureHubId == null || arrivalHubId == null) {
            throw new IllegalArgumentException("출발/도착 허브 ID는 필수입니다");
        }
        if (departureHubId.equals(arrivalHubId)) {
            throw new IllegalArgumentException("출발 허브와 도착 허브는 달라야 합니다");
        }
    }

    private static void validateEstimatedValues(BigDecimal distance, Integer duration) {
        if (distance == null || distance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("예상 거리는 0보다 커야 합니다");
        }
        if (duration == null || duration <= 0) {
            throw new IllegalArgumentException("예상 소요 시간은 0보다 커야 합니다");
        }
    }

    private static void validateDeliveryManager(DeliveryManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("배송 담당자는 필수입니다");
        }
        if (!manager.getType().isHubDelivery()) {
            throw new IllegalArgumentException("허브 간 경로는 허브 배송 담당자만 담당할 수 있습니다");
        }
    }

    private void validateActualValues(BigDecimal distance, Integer duration) {
        if (distance == null || distance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("실제 거리는 0보다 커야 합니다");
        }
        if (duration == null || duration <= 0) {
            throw new IllegalArgumentException("실제 소요 시간은 0보다 커야 합니다");
        }
    }

    /**
     * 경로 상태 변경
     */
    public void changeStatus(DeliveryRouteStatus newStatus, BigDecimal actualDistance, Integer actualDuration) {
        this.status.validateTransition(newStatus);

        if (newStatus.isArrived()) {
            validateActualValues(actualDistance, actualDuration);
            this.actualDistance = actualDistance;
            this.actualDuration = actualDuration;
        }

        this.status = newStatus;
    }
}
package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "p_delivery")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;

    @Column(name = "order_id", nullable = false, unique = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DeliveryStatus status;

    @Column(name = "departure_hub_id", nullable = false)
    private UUID departureHubId;

    @Column(name = "arrival_hub_id", nullable = false)
    private UUID arrivalHubId;

    @Embedded
    private DeliveryAddress deliveryAddress;

    @Embedded
    private Recipient recipient;

    /**
     * 업체 배송 담당자 (도착 허브 → 최종 목적지)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_delivery_manager_id", nullable = false)
    private DeliveryManager companyDeliveryManager;

    /**
     * 배송 경로 1:N (단방향, 부모가 FK 관리)
     * - 생성/갱신 위해 PERSIST, MERGE 사용
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "delivery_id", nullable = false, updatable = false)
    @OrderBy("sequence ASC")
    private List<DeliveryRoute> routes = new ArrayList<>();

    /**
     * 배송 생성
     * Order Service에서 주문 생성 시 호출
     */
    public static Delivery create(
            UUID orderId,
            UUID departureHubId,
            UUID arrivalHubId,
            DeliveryAddress deliveryAddress,
            Recipient recipient,
            DeliveryManager companyDeliveryManager,
            List<DeliveryRoute> routes
    ) {
        validateOrderId(orderId);
        validateHubIds(departureHubId, arrivalHubId);
        validateCompanyDeliveryManager(companyDeliveryManager);
        validateRoutes(routes);

        // null 제거 + sequence 기준 정렬
        List<DeliveryRoute> normalizedRoutes = routes.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(DeliveryRoute::getSequence)) // 연결성 검증은 제외
                .collect(Collectors.toList());

        return Delivery.builder()
                .orderId(orderId)
                .status(DeliveryStatus.HUB_WAITING)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .deliveryAddress(deliveryAddress)
                .recipient(recipient)
                .companyDeliveryManager(companyDeliveryManager)
                .routes(normalizedRoutes)
                .build();
    }

    private static void validateOrderId(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("주문 ID는 필수입니다");
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

    private static void validateCompanyDeliveryManager(DeliveryManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("업체 배송 담당자는 필수입니다");
        }
    }

    private static void validateRoutes(List<DeliveryRoute> routes) {
        if (routes == null || routes.isEmpty()) {
            throw new IllegalArgumentException("배송 경로는 최소 1개 이상이어야 합니다.");
        }
    }

    /**
     * 배송 상태 변경
     * @param byApi true면 API 요청으로 인한 변경, false면 시스템 내부(허브 경로 전파 등)에 의한 변경
     */
    public void changeStatus(DeliveryStatus newStatus, boolean byApi) {
        // 상태 값 검증
        validateStatus(newStatus);

        // API 호출일 때 허브 구간 상태로 변경 금지
        if (byApi && newStatus.isHubPhase()) {
            throw new IllegalStateException("허브 구간 상태로는 API로 직접 변경할 수 없습니다. 경로 진행으로만 변경됩니다.");
        }

        // 동일 상태면 무시
        if (this.status == newStatus) return;

        // 상태 전환 규칙 검증
        this.status.validateTransition(newStatus);
        // 업체 구간 전환 제약 검증
        validateCompanyPhaseTransition(newStatus);

        // 상태 변경
        this.status = newStatus;
    }

    private static void validateStatus(DeliveryStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("변경할 배송 상태는 필수입니다.");
        }
    }

    /**
     * HUB_ARRIVED 이후에만 업체 배송 가능,
     * COMPANY_MOVING 이후에만 배송 완료 가능
     */
    private void validateCompanyPhaseTransition(DeliveryStatus nextStatus) {
        // HUB_ARRIVED 이후에만 업체 배송 시작 가능
        if (nextStatus.isCompanyMoving() && !this.status.isHubArrived()) {
            throw new IllegalStateException("목적지 허브 도착(HUB_ARRIVED) 이후에만 업체 배송을 시작할 수 있습니다.");
        }

        // COMPANY_MOVING 이후에만 배송 완료 가능
        if (nextStatus.isCompleted() && !this.status.isCompanyMoving()) {
            throw new IllegalStateException("업체 배송 중(COMPANY_MOVING) 상태에서만 배송 완료가 가능합니다.");
        }
    }

    // 속한 허브인지
    public boolean isRelatedToHub(UUID hubId) {
        if (hubId == null) {
            return false;
        }
        return departureHubId.equals(hubId) || arrivalHubId.equals(hubId);
    }

    // 업체 배송 담당자 인지
    public boolean isAssignedTo(Long userId) {
        return this.companyDeliveryManager.getDeliveryManagerId().equals(userId);
    }

     // 특정 허브가 도착 허브인지
    public boolean isArrivalHub(UUID hubId) {
        if (hubId == null || this.arrivalHubId == null) {
            return false;
        }
        return this.arrivalHubId.equals(hubId);
    }

    public void deleteCascade(Long deletedBy) {
        if (isDeleted()) return; // 중복 방지

        // 경로 논리삭제
        if (routes != null && !routes.isEmpty()) {
            routes.stream()
                    .filter(r -> !r.isDeleted())
                    .forEach(r -> r.delete(deletedBy));
        }

        // 배송 논리삭제
        this.delete(deletedBy);
    }
}

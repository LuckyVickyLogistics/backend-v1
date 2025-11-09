package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_delivery_manager_id", nullable = false)
    private DeliveryManager companyDeliveryManager;

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
            DeliveryManager companyDeliveryManager
    ) {
        validateOrderId(orderId);
        validateHubIds(departureHubId, arrivalHubId);
        validateCompanyDeliveryManager(companyDeliveryManager);

        return Delivery.builder()
                .orderId(orderId)
                .status(DeliveryStatus.HUB_WAITING)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .deliveryAddress(deliveryAddress)
                .recipient(recipient)
                .companyDeliveryManager(companyDeliveryManager)
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

    /**
     * 배송 상태 변경
     */
    public void changeStatus(DeliveryStatus newStatus) {
        this.status.validateTransition(newStatus);
        this.status = newStatus;
    }
}

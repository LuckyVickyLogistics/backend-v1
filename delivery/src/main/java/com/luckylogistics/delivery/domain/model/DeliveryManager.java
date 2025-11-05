package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_delivery_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryManager extends BaseEntity {

    @Id
    @Column(name = "delivery_manager_id")
    private Long deliveryManagerId; // userId와 동일한 값 사용 (PK)

    @Column(name = "hub_id")
    private String hubId; // UUID를 String으로 저장 (COMPANY_DELIVERY만 필수)

    @Column(name = "slack_id", nullable = false, length = 100)
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private DeliveryManagerType type;

    @Column(name = "delivery_sequence", nullable = false)
    private Integer deliverySequence;

    @Builder
    public DeliveryManager(
            Long deliveryManagerId,
            String hubId,
            String slackId,
            DeliveryManagerType type,
            Integer deliverySequence
    ) {
        this.deliveryManagerId = deliveryManagerId;
        this.hubId = hubId;
        this.slackId = slackId;
        this.type = type;
        this.deliverySequence = deliverySequence;
    }
}
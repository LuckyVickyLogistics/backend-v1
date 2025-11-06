package com.luckylogistics.delivery.domain.model;

import com.luckylogistics.delivery.domain.vo.HubId;
import com.luckylogistics.delivery.domain.vo.SlackId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "p_delivery_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryManager extends BaseEntity {

    @Id
    @Column(name = "delivery_manager_id", nullable = false)
    private Long deliveryManagerId; // userId와 동일한 값 사용 (PK)

    // COMPANY_DELIVERY만 보유, HUB_DELIVERY는 null
    @Embedded
    @AttributeOverride(name = "hubId", column = @Column(name = "hub_id", columnDefinition = "uuid", nullable = true))
    private HubId hubId;

    @Embedded
    @AttributeOverride(name = "slackId", column = @Column(name = "slack_id", nullable = false, length = 100))
    private SlackId slackId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private DeliveryManagerType type;

    @Column(name = "delivery_sequence", nullable = false)
    private Integer deliverySequence;

    /**
     * 배송 담당자 생성
     */
    public static DeliveryManager create(
            Long deliveryManagerId,
            HubId hubId,
            SlackId slackId,
            DeliveryManagerType type,
            Integer deliverySequence
    ) {
        // 필수 입력 값 검증
        validateUserId(deliveryManagerId);
        validateType(type);
        validateDeliverySequence(deliverySequence);
        // 타입별 허브 ID 검증
        type.validateHubId(hubId);

        return DeliveryManager.builder()
                .deliveryManagerId(deliveryManagerId)
                .hubId(hubId)
                .slackId(slackId)
                .type(type)
                .deliverySequence(deliverySequence)
                .build();
    }

    private static void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("사용자 ID는 양수여야 합니다");
        }
    }

    private static void validateDeliverySequence(Integer sequence) {
        if (sequence == null || sequence < 0) {
            throw new IllegalArgumentException("배송 순번은 0 이상이어야 합니다");
        }
    }

    private static void validateType(DeliveryManagerType type) {
        if (type == null) {
            throw new IllegalArgumentException("배송 담당자 타입은 필수입니다.");
        }
    }

    /**
     * 배송 담당자 정보 수정
     */
    public void update(HubId newHubId, SlackId newSlackId, DeliveryManagerType newType, Integer newDeliverySequence) {
        validateType(newType);
        validateDeliverySequence(newDeliverySequence);
        // 타입에 따른 허브 ID 검증
        newType.validateHubId(newHubId);

        this.hubId = newHubId;
        this.slackId = newSlackId;
        this.type = newType;
        this.deliverySequence = newDeliverySequence;
    }

    /**
     * 특정 허브 소속 여부 확인 (HUB_MANAGER 권한 검증용)
     * 1. HUB_DELIVERY: 허브 간 배송이므로 특정 허브에 속하지 않음 → false
     * 2. COMPANY_DELIVERY: 특정 허브의 업체 배송 담당자 → hubId 일치 여부 확인
     */
    public boolean belongsToHub(HubId targetHubId) {
        // HUB_DELIVERY는 어떤 허브에도 속하지 않음
        if (this.type == DeliveryManagerType.HUB_DELIVERY) {
            return false;
        }

        // COMPANY_DELIVERY인데 hubId가 없으면 데이터 오류
        if (this.hubId == null) {
            throw new IllegalStateException("업체 배송 담당자는 허브 ID를 반드시 가져야 합니다");
        }

        // targetHubId가 null이면 비교 불가
        if (targetHubId == null) {
            return false;
        }

        // 허브 ID 일치 여부 확인
        return this.hubId.equals(targetHubId);
    }
}
package com.luckylogistics.delivery.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 배송 상태
 */
@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    HUB_WAITING("허브 대기") {
        @Override
        public boolean canTransitionTo(DeliveryStatus newStatus) {
            return newStatus == HUB_MOVING;
        }
    },

    HUB_MOVING("허브 간 이동 중") {
        @Override
        public boolean canTransitionTo(DeliveryStatus newStatus) {
            return newStatus == HUB_ARRIVED;
        }
    },

    HUB_ARRIVED("목적지 허브 도착") {
        @Override
        public boolean canTransitionTo(DeliveryStatus newStatus) {
            return newStatus == COMPANY_MOVING;
        }
    },

    COMPANY_MOVING("업체 배송 중") {
        @Override
        public boolean canTransitionTo(DeliveryStatus newStatus) {
            return newStatus == COMPLETED;
        }
    },

    COMPLETED("배송 완료") {
        @Override
        public boolean canTransitionTo(DeliveryStatus newStatus) {
            return false; // 완료 후 상태 변경 불가
        }
    };

    private final String description;

    /**
     * 상태 전환 가능 여부 검증
     */
    public abstract boolean canTransitionTo(DeliveryStatus newStatus);

    /**
     * 상태 전환 검증 및 예외 발생
     */
    public void validateTransition(DeliveryStatus newStatus) {
        if (!canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("%s 상태에서 %s 상태로 변경할 수 없습니다",
                            this.description, newStatus.description));
        }
    }

    /** 업체 배송 중 상태 확인 */
    public boolean isCompanyMoving() {
        return this == COMPANY_MOVING;
    }

    /** 배송 완료 상태 확인 */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /** 목적지 허브 도착 상태 확인 */
    public boolean isHubArrived() {
        return this == HUB_ARRIVED;
    }

    /** 허브 대기 상태 확인 */
    public boolean isHubWaiting() {
        return this == HUB_WAITING;
    }

    /** 허브 이동 중 상태 확인 */
    public boolean isHubMoving() {
        return this == HUB_MOVING;
    }

    /** 허브 관련 단계 확인 (대기/이동중/도착) */
    public boolean isHubPhase() {
        return this == HUB_WAITING || this == HUB_MOVING || this == HUB_ARRIVED;
    }

    /** 업체 관련 단계 확인 (배송중/완료) */
    public boolean isCompanyPhase() {
        return this == COMPANY_MOVING || this == COMPLETED;
    }
}

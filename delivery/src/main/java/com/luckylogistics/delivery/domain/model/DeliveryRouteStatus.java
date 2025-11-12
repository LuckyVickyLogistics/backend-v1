package com.luckylogistics.delivery.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 배송 경로 상태
 */
@Getter
@RequiredArgsConstructor
public enum DeliveryRouteStatus {

    HUB_WAITING("허브 대기") {
        @Override
        public boolean canTransitionTo(DeliveryRouteStatus newStatus) {
            return newStatus == HUB_MOVING;
        }
    },

    HUB_MOVING("허브 이동 중") {
        @Override
        public boolean canTransitionTo(DeliveryRouteStatus newStatus) {
            return newStatus == HUB_ARRIVED;
        }
    },

    HUB_ARRIVED("허브 도착") {
        @Override
        public boolean canTransitionTo(DeliveryRouteStatus newStatus) {
            return false; // 도착 후 상태 변경 불가
        }
    };

    private final String description;

    /**
     * 상태 전환 가능 여부 검증
     */
    public abstract boolean canTransitionTo(DeliveryRouteStatus newStatus);

    /**
     * 상태 전환 검증 및 예외 발생
     */
    public void validateTransition(DeliveryRouteStatus newStatus) {
        if (!canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("%s 상태에서 %s 상태로 변경할 수 없습니다",
                            this.description, newStatus.description));
        }
    }

    /**
     * 경로 도착 여부
     */
    public boolean isArrived() {
        return this == DeliveryRouteStatus.HUB_ARRIVED;
    }

    /**
     * 경로 진행 중 여부
     */
    public boolean isInTransit() {
        return this == DeliveryRouteStatus.HUB_MOVING;
    }
}
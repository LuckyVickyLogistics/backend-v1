package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateDeliveryRouteStatusRequest(
        @NotNull(message = "변경할 상태는 필수입니다")
        DeliveryRouteStatus status,

        BigDecimal actualDistance,
        Integer actualDuration
) {
    // HUB_ARRIVED 상태로 변경 시 데이터 검증
    public void validateForArrived() {
        if (status == DeliveryRouteStatus.HUB_ARRIVED) {
            if (actualDistance == null) {
                throw new IllegalArgumentException("경로 완료 시 실제 거리는 필수입니다");
            }
            if (actualDuration == null) {
                throw new IllegalArgumentException("경로 완료 시 실제 소요 시간은 필수입니다");
            }
        }
    }
}
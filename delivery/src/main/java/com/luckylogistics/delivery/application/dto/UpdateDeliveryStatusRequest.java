package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryStatusRequest(
        @NotNull(message = "변경할 상태는 필수입니다")
        DeliveryStatus status
) {}
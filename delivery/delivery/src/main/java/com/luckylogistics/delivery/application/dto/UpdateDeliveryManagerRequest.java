package com.luckylogistics.delivery.application.dto;

import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

/**
 * 배송 담당자 수정 요청 DTO
 */
public record UpdateDeliveryManagerRequest(
        UUID hubId,

        @NotBlank(message = "슬랙 ID는 필수입니다.")
        @Email(message = "올바른 이메일 형식의 Slack ID가 아닙니다.")
        String slackId,

        @NotNull(message = "배송 담당자 타입은 필수입니다")
        DeliveryManagerType type,

        @NotNull(message = "근무 시작 시간은 필수입니다.")
        LocalTime startTime,

        @NotNull(message = "근무 종료 시간은 필수입니다.")
        LocalTime endTime
) {
}
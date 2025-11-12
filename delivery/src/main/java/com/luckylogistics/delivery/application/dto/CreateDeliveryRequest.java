package com.luckylogistics.delivery.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * 배송 생성 요청 DTO
 */
public record CreateDeliveryRequest(
        @NotNull(message = "주문 ID는 필수입니다")
        UUID orderId,

        @NotNull(message = "출발 허브 ID는 필수입니다")
        UUID departureHubId,

        @NotNull(message = "도착 허브 ID는 필수입니다")
        UUID arrivalHubId,

        @NotBlank(message = "배송지 주소는 필수입니다")
        @Size(max = 500, message = "배송지 주소는 500자 이하여야 합니다")
        String deliveryAddress,

        @NotBlank(message = "수령인 이름은 필수입니다")
        @Size(max = 100, message = "수령인 이름은 100자 이하여야 합니다")
        String recipientName,

        @NotBlank(message = "수령인 슬랙 ID는 필수입니다")
        @Email(message = "올바른 이메일 형식의 Slack ID가 아닙니다.")
        String recipientSlackId
) {}
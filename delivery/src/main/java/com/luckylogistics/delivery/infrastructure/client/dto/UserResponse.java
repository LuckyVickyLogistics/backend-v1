package com.luckylogistics.delivery.infrastructure.client.dto;

import com.luckylogistics.delivery.common.enums.UserOrganizationType;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.delivery.common.enums.UserStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID identifier,
        String username,
        String slackId,
        UserRole role,
        UserOrganizationType organizationType, // HUB, COMPANY
        UUID organizationId,
        UserStatus status,
        boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // 응답 데이터 유효성 검증
    public void validate() {
        if (identifier == null) {
            throw new IllegalStateException("[UserClient] identifier가 유효하지 않습니다");
        }
        if (role == null) {
            throw new IllegalStateException("[UserClient] role이 유효하지 않습니다");
        }
        if (organizationType == null || organizationId == null) {
            throw new IllegalStateException("[UserClient] organization 정보가 유효하지 않습니다");
        }
    }

    // 사용자 권한 검증 - 배송 담당자 여부 확인
    public void validateDeliveryManagerRole() {
        if (role == null || !role.isDeliveryManager()) {
            throw new IllegalStateException("[UserClient] 배송 담당자 권한이 아닙니다");
        }
    }
}
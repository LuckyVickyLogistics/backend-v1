package com.luckylogistics.delivery.infrastructure.client.dto;

import com.luckylogistics.delivery.common.enums.UserRole;

// TODO: User Service 연동
public record UserResponse(
        Long userId,
        UserRole role
) {
}
package com.luckylogistics.product.infrastructure.client.dto;

import com.luckylogistics.common.enums.UserRole;

// TODO: User Service 연동
public record UserResponse(
        Long userId,
        UserRole role
) {
}

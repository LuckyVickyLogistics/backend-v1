package com.luckylogistics.company.infrastructure.client.dto;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.company.infrastructure.client.dto.enums.OrganizationType;
import com.luckylogistics.company.infrastructure.client.dto.enums.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailResponse(
    Long userId,
    UUID identifier,
    String username,
    String slackId,
    UserRole role,
    OrganizationType organizationType,
    UUID organizationId,
    Status status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

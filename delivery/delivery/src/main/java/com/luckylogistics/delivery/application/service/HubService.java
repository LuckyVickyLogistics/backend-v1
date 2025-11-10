package com.luckylogistics.delivery.application.service;

import java.util.UUID;

public interface HubService {

    /**
     * 허브 존재 여부 검증
     */
    void validateHubExists(UUID hubId);

    /**
     * 요청 사용자의 Hub ID 조회
     * HUB_MANAGER의 담당 허브 확인 (권한)
     */
    public UUID getUserHubId(Long userId);
}

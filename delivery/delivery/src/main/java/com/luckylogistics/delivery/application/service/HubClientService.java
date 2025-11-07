package com.luckylogistics.delivery.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

public interface HubClientService {

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

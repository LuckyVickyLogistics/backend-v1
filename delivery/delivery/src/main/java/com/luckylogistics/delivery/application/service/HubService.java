package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.DeliveryRoutePlan;

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
    UUID getUserHubId(Long userId);

    /**
     * 배송 경로 조회
     * 허브 간 거리, 예상 소요 시간 등을 포함한 경로 정보를 반환
     */
    DeliveryRoutePlan getDeliveryRoutePlan(UUID departureHubId, UUID arrivalHubId);
}

package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.dto.DeliveryRoutePlan;
import com.luckylogistics.delivery.application.service.HubService;
import com.luckylogistics.delivery.infrastructure.client.dto.HubResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.HubRoutePlanResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubAdapter implements HubService {

     private final HubFeignClient hubClient;

    // 허브 존재 여부 검증
    @Override
    public void validateHubExists(UUID hubId) {
        // 허브 존재 하는지
        HubResponse response = hubClient.getHub(hubId).data();
        response.validate();
        log.debug("[HubClient] 허브 존재 확인 완료. hubId: {}", response.hubId());
    }

    // 요청 사용자의 Hub ID 조회
    // 허브 관리자의 담당 허브 확인 (권한)
    @Override
    public UUID getHubByUserId(Long userId) {
        HubResponse response = hubClient.getHubByUserId(userId).data();
        response.validate();
        log.info("[HubClient] 사용자의 담당 허브 조회 완료. hubId: {}", response.hubId());
        return response.hubId();
    }

    // 배송 경로 계획 조회
    // 출발/도착 허브 간 경로 정보를 Hub 서비스에서 조회
    @Override
    public DeliveryRoutePlan getDeliveryRoutePlan(UUID departureHubId, UUID arrivalHubId) {
        HubRoutePlanResponse response = hubClient.getHubRoutePlan(departureHubId, arrivalHubId).data();
        response.validate();
        log.info("[HubClient] 배송 경로 계획 조회 완료. departureHubId: {}, arrivalHubId: {}", departureHubId, arrivalHubId);
        return response.toDeliveryRoutePlan();
    }
}
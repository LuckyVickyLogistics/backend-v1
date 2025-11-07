package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.service.HubClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubClientAdapter implements HubClientService {
    // TODO: 외부 서비스 클라이언트 주입
    // private final HubFeignClient hubFeignClient;

    /**
     * 허브 존재 여부 검증
     */
    public void validateHubExists(UUID hubId) {
        // TODO: Hub Service 연동
        // 허브 존재 하는지
        // HubResponse hub = hubClient.getHub(hubId);

        log.warn("[TODO] Hub Service 연동 필요 - hubId 검증 생략: {}", hubId);
    }

    /**
     * 요청 사용자의 Hub ID 조회
     * HUB_MANAGER의 담당 허브 확인 (권한)
     */
    public UUID getUserHubId(Long userId) {
        // TODO: Hub Service 연동
        // HubManagerResponse manager = hubClient.getHubManager(userId);
        // return manager.hubId();

        // FIXME: 임시 하드코딩
        UUID tempHubId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        log.warn("[TODO] Hub Service 연동 필요 - hubId 하드코딩: {}", tempHubId);
        return tempHubId;
    }
}


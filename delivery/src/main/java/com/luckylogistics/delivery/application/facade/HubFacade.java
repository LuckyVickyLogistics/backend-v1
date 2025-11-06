package com.luckylogistics.delivery.application.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 외부 Hub 서비스 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HubFacade {

    // TODO: 외부 서비스 클라이언트 주입
    // private final HubClient hubClient;

    /**
     * 허브 존재 여부 검증
     */
    public void validateHubExists(UUID hubId) {
        // TODO: Hub Service 연동
        // 허브 존재 하는지

        log.warn("[TODO] Hub Service 연동 필요 - hubId 검증 생략: {}", hubId);
    }
}

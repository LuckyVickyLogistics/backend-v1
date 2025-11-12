package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.dto.DeliveryRoutePlan;
import com.luckylogistics.delivery.application.service.HubService;
import com.luckylogistics.delivery.infrastructure.client.dto.HubRouteResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.HubRoutePlanResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubAdapter implements HubService {

    // TODO: 외부 서비스 클라이언트 주입
    // private final HubFeignClient hubClient;

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

    @Override
    public DeliveryRoutePlan getDeliveryRoutePlan(UUID departureHubId, UUID arrivalHubId) {
        // TODO: Hub Service 연동
        // FIXME: 임시 하드코딩 (Hub Service 연동 시 제거 예정)
        List<HubRouteResponse> tempRoutes = List.of(
                HubRouteResponse.of(
                        1,
                        UUID.fromString("00000000-0000-0000-0000-000000000001"),
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        new BigDecimal("12.5"),
                        18
                ),
                HubRouteResponse.of(
                        2,
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        UUID.fromString("00000000-0000-0000-0000-000000000003"),
                        new BigDecimal("8.7"),
                        12
                )
        );

        // 총 거리 계산 (BigDecimal)
        BigDecimal tempTotalDistance = tempRoutes.stream()
                .map(HubRouteResponse::distanceKm)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 총 소요 시간 계산
        int tempTotalDuration = tempRoutes.stream()
                .mapToInt(HubRouteResponse::durationMinutes)
                .sum();

        // 응답 객체 생성
        // HubRoutePlanResponse response = hubClient.getLatestRoutePlan(departureHubId, arrivalHubId);
        HubRoutePlanResponse tempResponse = HubRoutePlanResponse.of(tempRoutes, tempTotalDistance, tempTotalDuration);

        log.warn("[TODO] Hub Service 연동 필요 - departureHubId={}, arrivalHubId={}, totalDistance={}km, totalDuration={}min",
                departureHubId, arrivalHubId, tempTotalDistance, tempTotalDuration);

        return tempResponse.toDeliveryRoutePlan();
        // return hubClient.calculateRoute(request);
    }
}
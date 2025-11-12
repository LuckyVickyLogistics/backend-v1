package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteStatusRequest;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.exception.BusinessException;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.repository.DeliveryRepository;
import com.luckylogistics.delivery.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRouteService {

    private final DeliveryRouteRepository routeRepository;
    private final DeliveryRepository deliveryRepository;
    private final HubService hubService;

    @Transactional
    public UpdateDeliveryRouteResponse updateDeliveryRouteStatus(
            UUID routeId,
            UpdateDeliveryRouteStatusRequest request,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        log.info("[DeliveryRoute] 배송 경로 상태 변경. routeId: {}, newStatus: {}", routeId, request.status());

        // 요청 검증
        request.validateForArrived();

        // 배송 루트 조회 (DeliveryRoute)
        DeliveryRoute route = findDeliveryRouteByIdWithManager(routeId);

        // 배송 경로 상태 변경 권한 검증
        validateStatusChangePermission(route, currentUserId, currentUserRole);

        // 배송 조회 (Delivery)
        Delivery delivery = findDeliveryById(route.getDeliveryId());

        // 배송 경로 상태 변경 (자신의 상태만)
        route.changeStatus(request.status(), request.actualDistance(), request.actualDuration());

        // 배송 상태 변경 (배송 전체 상태 동기화)
        delivery.syncStatusFromRoutes();

        log.info("[DeliveryRoute] 배송 경로 상태 변경 완료. routeId: {}, deliveryStatus: {}",
                routeId, delivery.getStatus());

        return UpdateDeliveryRouteResponse.from(route);
    }

    private DeliveryRoute findDeliveryRouteByIdWithManager(UUID deliveryRouteId) {
        return routeRepository.findByIdWithManager(deliveryRouteId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_ROUTE_NOT_FOUND));
    }

    private Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
    }

    /**
     * 상태 변경 권한 검증
     */
    private void validateStatusChangePermission(
            DeliveryRoute route,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        // 업체 관리자: 경로 상태 변경 불가
        if (currentUserRole.isCompanyManager()) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ROUTE_MODIFY);
        }

        // 마스터: 모든 경로 상태 변경 가능
        if (currentUserRole.isMaster()) {
            return;
        }

        // 허브 관리자: 담당 허브의 경로만 변경 가능
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            if (!route.isRelatedToHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_NOT_HUB_ROUTE);
            }
            return;
        }

        // 배송 담당자: 본인이 담당하는 경로만 변경 가능
        if (currentUserRole.isDeliveryManager()) {
            if (!route.isAssignedTo(currentUserId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_NOT_ASSIGNED_ROUTE);
            }
            return;
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_ROUTE_MODIFY);
    }
}

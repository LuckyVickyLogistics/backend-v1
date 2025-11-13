package com.luckylogistics.delivery.application.service;

import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import com.luckylogistics.common.infrastructure.util.PageableUtils;
import com.luckylogistics.delivery.application.dto.DeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryRouteStatusRequest;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import com.luckylogistics.delivery.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRouteService {

    private final DeliveryRouteRepository routeRepository;
    private final DeliveryService deliveryService;
    private final DeliveryManagerRepository managerRepository;
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

        // 배송 경로 조회 (DeliveryRoute)
        DeliveryRoute route = findDeliveryRouteByIdWithManager(routeId);

        // 배송 경로 상태 변경 권한 검증
        validateStatusChangePermission(route, currentUserId, currentUserRole);

        // 배송 조회 (Delivery)
        Delivery delivery = deliveryService.findDeliveryById(route.getDeliveryId());

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
            UUID userHubId = hubService.getHubByUserId(currentUserId);
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

    public DeliveryRouteResponse getDeliveryRoute(
            UUID routeId,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        log.info("[DeliveryRoute] 배송 경로 조회. routeId: {}, userId: {}", routeId, currentUserId);

        // 배송 경로 조회
        DeliveryRoute route = findDeliveryRouteByIdWithManager(routeId);
        // 배송 조회
        Delivery delivery = deliveryService.findDeliveryById(route.getDeliveryId());
        // 조회 권한 검증
        validateReadPermission(delivery, route, currentUserId, currentUserRole);
        // 응답 반환
        return DeliveryRouteResponse.from(route);
    }

    /**
     * 조회 권한 검증
     */
    private void validateReadPermission(
            Delivery delivery,
            DeliveryRoute route,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        if (currentUserRole == null) throw new BusinessException(ErrorCode.FORBIDDEN_ROUTE_READ);

        // 마스터, 업체 관리자: 모든 경로 조회 가능
        if (currentUserRole.isMaster() || currentUserRole.isCompanyManager()) {
            return;
        }

        // 허브 관리자: 담당 허브의 경로만 조회 가능
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId);

            if (route != null && !route.isRelatedToHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_NOT_HUB_ROUTE);
            }

            if (!delivery.isRelatedToHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_NOT_HUB_DELIVERY);
            }
            return;
        }

        // 배송 담당자: 본인이 담당하는 배송의 경로만 조회 가능
        if (currentUserRole.isDeliveryManager()) {
            boolean isCompanyManager = delivery.getCompanyDeliveryManager()
                    .getDeliveryManagerId().equals(currentUserId);

            boolean isRouteManager = route != null && route.isAssignedTo(currentUserId);

            boolean isAnyRouteManager = delivery.getRoutes().stream()
                    .anyMatch(r -> r.isAssignedTo(currentUserId));

            if (!isCompanyManager && !isRouteManager && !isAnyRouteManager) {
                throw new BusinessException(ErrorCode.FORBIDDEN_NOT_ASSIGNED_ROUTE);
            }
            return;
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_ROUTE_READ);
    }

    /**
     * 배송 담당자별 경로 목록 조회
     * - MASTER: 모든 담당자의 경로 조회 가능
     * - COMPANY_MANAGER: 모든 담당자의 경로 조회 가능
     * - HUB_MANAGER: 모든 담당자의 경로 조회 가능
     * - DELIVERY_MANAGER: 본인 경로만 조회 가능 (허브배송담당자만 경로 존재)
     */
    public Page<DeliveryRouteResponse> getDeliveryRoutesByManager(
            Long deliveryManagerId,
            DeliveryRouteStatus status,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        // 조회 대상 결정
        Long targetManagerId = determineTargetManagerId(deliveryManagerId, currentUserId, currentUserRole);

        log.info("[DeliveryRoute] 배송 담당자별 경로 조회. targetManagerId: {}, status: {}, userId: {}, role: {}",
                targetManagerId, status, currentUserId, currentUserRole);

        // 권한 검증
        validateSearchPermission(targetManagerId, currentUserId, currentUserRole);

        // 전체 조회(null)일 땐 호출하지 않음
        if (targetManagerId != null) {
            validateManagerExists(targetManagerId);
        }

        // 페이징
        Pageable pageable = PageableUtils.createPageable(page, size, sortBy, direction);

        // 경로 조회
        Page<DeliveryRoute> routes = routeRepository.searchByHubDeliveryManagerIdAndStatus(
                targetManagerId, status, pageable
        );

        return routes.map(DeliveryRouteResponse::from);
    }

    // 담당자 ID 결정
    // DELIVERY_MANAGER: null이면 본인 지정
    private Long determineTargetManagerId(
            Long deliveryManagerId,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        if (currentUserRole.isDeliveryManager()) {
            if (deliveryManagerId == null) return currentUserId; // deliveryManagerId가 null이면 본인 조회

            if (!deliveryManagerId.equals(currentUserId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MANAGER_ROUTE);
            }
            return deliveryManagerId;
        }
        // MASTER / COMPANY_MANAGER / HUB_MANAGER: 전체(null 허용), 특정인 가능
        return deliveryManagerId;
    }

    /**
     * 권한별 접근 검증
     */
    private void validateSearchPermission(
            Long targetManagerId,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        // 마스터, 업체 관리자, 허브 관리자: 모든 담당자 조회 가능
        if (currentUserRole.isMaster() || currentUserRole.isCompanyManager() || currentUserRole.isHubManager()) {
            return;
        }

        // 배송 담당자: 본인만 조회 가능
        if (currentUserRole.isDeliveryManager()) {
            if (!Objects.equals(targetManagerId, currentUserId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MANAGER_ROUTE);
            }
            return;
        }
        throw new BusinessException(ErrorCode.FORBIDDEN_ROUTE_SEARCH);
    }

    // ID로 배송 담당자 조회
    private void validateManagerExists(Long deliveryManagerId) {
        if (!managerRepository.existsById(deliveryManagerId)) {
            throw new BusinessException(ErrorCode.DELIVERY_MANAGER_NOT_FOUND);
        }
    }
}
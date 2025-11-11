package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.*;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.exception.BusinessException;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import com.luckylogistics.delivery.common.util.PageableUtils;
import com.luckylogistics.delivery.domain.model.*;
import com.luckylogistics.delivery.domain.repository.DeliveryRepository;
import com.luckylogistics.delivery.domain.service.DeliveryDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 배송 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryDomainService domainService;

    private final HubService hubService;
    private final OrderService orderService;

    /**
     * 배송 생성
     */
    @Transactional
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request, Long currentUserId) {
        log.info("[Delivery] 배송 생성 시작. orderId: {}", request.orderId());

        // 기본 검증
        if (deliveryRepository.existsByOrderId(request.orderId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_DELIVERY);
        }
        orderService.validateOrderExists(request.orderId());
        hubService.validateHubExists(request.departureHubId());
        hubService.validateHubExists(request.arrivalHubId());

        // 경로 계획 가져오기
        DeliveryRoutePlan plan = hubService.getDeliveryRoutePlan(
                request.departureHubId(), request.arrivalHubId()
        );

        // 허브 배송 담당자 배정
        DeliveryManager hubManager = domainService.assignHubDeliveryManager();

        // 배송 경로 생성
        List<DeliveryRoute> routes = plan.routes().stream()
                .map(seg -> DeliveryRoute.create(
                        seg.sequence(),
                        seg.departureHubId(),
                        seg.arrivalHubId(),
                        seg.distanceKm(),
                        seg.durationMinutes(),
                        hubManager
                ))
                .collect(Collectors.toList());

        // 업체 배송 담당자 배정
        DeliveryManager companyManager = domainService.assignCompanyDeliveryManager(request.arrivalHubId());

        // 배송 생성(경로 리스트 포함) → 부모만 저장하면 자식도 PERSIST
        Delivery delivery = Delivery.create(
                request.orderId(),
                request.departureHubId(),
                request.arrivalHubId(),
                DeliveryAddress.of(request.deliveryAddress()),
                Recipient.of(request.recipientName(), request.recipientSlackId()),
                companyManager,
                routes
        );

        Delivery savedDelivery = deliveryRepository.save(delivery); // cascade로 routes INSERT + FK 주입

        log.info("[Delivery] 배송 생성 완료. deliveryId: {}, routes: {}",
                savedDelivery.getDeliveryId(), savedDelivery.getRoutes().size());

        // 응답 반환
        return CreateDeliveryResponse.from(savedDelivery);
    }

    public DeliveryResponse getDelivery(UUID deliveryId, Long currentUserId, UserRole currentUserRole) {
        Delivery delivery = findDeliveryByIdWithRoutes(deliveryId);
        validateReadPermission(delivery, currentUserId, currentUserRole);

        return DeliveryResponse.from(delivery);
    }

    private Delivery findDeliveryById(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
    }

    private Delivery findDeliveryByIdWithRoutes(UUID id) {
        return deliveryRepository.findByIdWithRoutes(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
    }

    private void validateReadPermission(Delivery delivery, Long currentUserId, UserRole currentUserRole) {
        if (currentUserRole == null) throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);

        // 마스터 / 업체 관리자: 항상 허용
        if (currentUserRole.isMaster() || currentUserRole.isCompanyManager()) return;

        // 허브 관리자: 배송의 출발/도착 허브, 경로에 포함된 모든 허브 관리자는 접근 가능
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            boolean permitted =
                    delivery.isRelatedToHub(userHubId) ||
                            delivery.getRoutes().stream().anyMatch(r -> r.isRelatedToHub(userHubId));

            if (!permitted) throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
            return;
        }

        // 배송 담당자: 업체 배송담당자, 어떤 경로의 허브 배송담당자도 허용
        if (currentUserRole.isDeliveryManager()) {
            boolean isCompanyDeliveryManager = delivery.isAssignedTo(currentUserId);
            boolean isAnyHubRouteManager = delivery.getRoutes().stream()
                    .anyMatch(r -> r.getHubDeliveryManager() != null
                            && currentUserId.equals(r.getHubDeliveryManager().getDeliveryManagerId()));

            if (!(isCompanyDeliveryManager || isAnyHubRouteManager)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
            }
            return;
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
    }

    @Transactional
    public UpdateDeliveryResponse updateDeliveryStatus(
            UUID deliveryId,
            UpdateDeliveryStatusRequest request,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        log.info("[Delivery] 배송 상태 변경. deliveryId: {}, newStatus: {}", deliveryId, request.status());

        // 배송 조회
        Delivery delivery = findDeliveryById(deliveryId);
        // 권한 검증 (마스터/해당 허브관리자/업체배송담당자)
        validateStatusChangePermission(delivery, currentUserId, currentUserRole);
        // 상태 변경
        delivery.changeStatus(request.status(), true);

        log.info("[Delivery] 배송 상태 변경 완료. deliveryId: {}", deliveryId);
        return UpdateDeliveryResponse.from(delivery);
    }

    private void validateStatusChangePermission(Delivery delivery, Long currentUserId, UserRole currentUserRole) {
        if (currentUserRole == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MODIFY);
        }

        // 마스터 관리자: 허용
        if (currentUserRole.isMaster()) return;

        // 회사(발주/수령) 관리자: 배송 상태 변경 불가
        if (currentUserRole.isCompanyManager()) {
            throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MODIFY);
        }

        // 허브 관리자: 도착 허브 관리자만 허용
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            if (!delivery.isArrivalHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MODIFY);
            }
            return;
        }

        // 배송 담당자: 본인에게 할당된 배송만 상태 변경 가능
        if (currentUserRole.isDeliveryManager()) {
            if (!delivery.isAssignedTo(currentUserId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MODIFY);
            }
            return;
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_MODIFY);
    }

    @Transactional
    public void deleteDelivery(UUID deliveryId, Long currentUserId, UserRole currentUserRole) {
        log.info("[Delivery] 배송 삭제. deliveryId: {}", deliveryId);

        Delivery delivery = findDeliveryByIdWithRoutes(deliveryId);
        validateDeletePermission(delivery, currentUserId, currentUserRole);

        delivery.deleteCascade(currentUserId);

        log.info("[Delivery] 배송 삭제 완료. deliveryId: {}", deliveryId);
    }

    private void validateDeletePermission(Delivery delivery, Long currentUserId, UserRole currentUserRole) {
        if (currentUserRole == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_DELETE);
        }

        // 마스터 관리자: 허용
        if (currentUserRole.isMaster()) return;

        // 허브 관리자: 본인 허브와 관련된 배송만 삭제 가능
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            if (!delivery.isRelatedToHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_DELETE);
            }
            return;
        }
        // 업체 담당자, 배송 담당자: 삭제 불가
        throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_DELETE);
    }

    public Page<DeliverySummaryResponse> getDeliveries(
            DeliveryStatus status,
            UUID departureHubId,
            UUID arrivalHubId,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        Pageable pageable = PageableUtils.createPageable(page, size, sortBy, direction);
        Page<Delivery> deliveries = findDeliveries(
                status, departureHubId, arrivalHubId, currentUserId, currentUserRole, pageable);

        return deliveries.map(DeliverySummaryResponse::from);
    }

    private Page<Delivery> findDeliveries(
            DeliveryStatus status,
            UUID departureHubId,
            UUID arrivalHubId,
            Long currentUserId,
            UserRole currentUserRole,
            Pageable pageable
    ) {
        if (currentUserRole == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_SEARCH);
        }

        if (currentUserId == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_SEARCH);
        }

        // 마스터 / 업체 관리자: 전체 조회 권한 + 필터
        if (currentUserRole.isMaster() || currentUserRole.isCompanyManager()) {
            return deliveryRepository.searchDeliveries(
                    status, departureHubId, arrivalHubId, pageable);
        }

        // 허브 관리자: 배송 경로에 내 허브가 포함된 배송만 조회 + 필터
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            if (userHubId == null) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_SEARCH);
            }
            // 출발/도착/경유 허브 모두 확인
            return deliveryRepository.searchByHubIdIncludingRoutes(
                    userHubId, status, departureHubId, arrivalHubId, pageable);
        }

        // 3. 배송 담당자: 내가 담당한 배송만 조회 + 필터
        if (currentUserRole.isDeliveryManager()) {
            // 업체 배송 담당자 / 허브 배송 담당자 통합 조회
            return deliveryRepository.searchByDeliveryManagerUserId(
                    currentUserId, status, departureHubId, arrivalHubId, pageable);
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_SEARCH);
    }
}
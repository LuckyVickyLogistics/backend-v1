package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.*;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.exception.BusinessException;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import com.luckylogistics.delivery.domain.model.*;
import com.luckylogistics.delivery.domain.repository.DeliveryRepository;
import com.luckylogistics.delivery.domain.repository.DeliveryRouteRepository;
import com.luckylogistics.delivery.domain.service.DeliveryDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 배송 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryDomainService domainService;

    private final HubService hubService;
    private final OrderService orderService;

    /**
     * 배송 생성
     */
    @Transactional
    public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request, Long currentUserId) {
        log.info("[Delivery] 배송 생성 시작. orderId: {}", request.orderId());

        if (deliveryRepository.existsByOrderId(request.orderId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_DELIVERY);
        }

        orderService.validateOrderExists(request.orderId());
        hubService.validateHubExists(request.departureHubId());
        hubService.validateHubExists(request.arrivalHubId());

        DeliveryRoutePlan deliveryRoutePlan = hubService.getDeliveryRoutePlan(
                request.departureHubId(), request.arrivalHubId()
        );

        DeliveryManager companyManager = domainService.assignCompanyDeliveryManager(request.arrivalHubId());

        Delivery delivery = Delivery.create(
                request.orderId(),
                request.departureHubId(),
                request.arrivalHubId(),
                DeliveryAddress.of(request.deliveryAddress()),
                Recipient.of(request.recipientName(), request.recipientSlackId()),
                companyManager
        );

        Delivery savedDelivery = deliveryRepository.save(delivery);

        for (DeliveryRouteSegment routeSegment : deliveryRoutePlan.routes()) {
            DeliveryManager hubManager = domainService.assignHubDeliveryManager();

            DeliveryRoute route = DeliveryRoute.create(
                    savedDelivery,
                    routeSegment.sequence(),
                    routeSegment.departureHubId(),
                    routeSegment.arrivalHubId(),
                    routeSegment.distanceKm(),
                    routeSegment.durationMinutes(),
                    hubManager
            );

            deliveryRouteRepository.save(route);
        }

        List<DeliveryRoute> routes = deliveryRouteRepository.findByDeliveryIdOrderBySequence(savedDelivery.getDeliveryId());

        log.info("[Delivery] 배송 생성 완료. deliveryId: {}, routes: {}", savedDelivery.getDeliveryId(), routes.size());

        return CreateDeliveryResponse.from(savedDelivery, routes);
    }

    public DeliveryResponse getDelivery(UUID deliveryId, Long currentUserId, UserRole currentUserRole) {
        Delivery delivery = findDeliveryById(deliveryId);
        validateReadPermission(delivery, currentUserId, currentUserRole);

        List<DeliveryRoute> routes = deliveryRouteRepository.findByDeliveryIdOrderBySequence(deliveryId);

        return DeliveryResponse.from(delivery, routes);
    }

    private Delivery findDeliveryById(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_NOT_FOUND));
    }

    private void validateReadPermission(Delivery delivery, Long currentUserId, UserRole currentUserRole) {
        if (currentUserRole.isMaster() || currentUserRole.isCompanyManager()) {
            return;
        }

        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getUserHubId(currentUserId);
            if (!delivery.isRelatedToHub(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
            }
            return;
        }

        if (currentUserRole.isDeliveryManager()) {
            if (!delivery.isAssignedTo(currentUserId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
            }
            return;
        }

        throw new BusinessException(ErrorCode.FORBIDDEN_DELIVERY_READ);
    }
}
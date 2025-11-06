package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.DeliveryManagerResponse;
import com.luckylogistics.delivery.application.facade.HubFacade;
import com.luckylogistics.delivery.application.facade.UserFacade;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.exception.BusinessException;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import com.luckylogistics.delivery.domain.service.DeliveryDomainService;
import com.luckylogistics.delivery.domain.vo.HubId;
import com.luckylogistics.delivery.domain.vo.SlackId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryManagerService {

    private final DeliveryManagerRepository repository;
    private final DeliveryDomainService domainService;
    private final UserFacade userFacade;
    private final HubFacade hubFacade;

    /**
     * 배송 담당자 생성
     */
    @Transactional
    public CreateDeliveryManagerResponse createDeliveryManager(
            CreateDeliveryManagerRequest request
    ) {
        log.info("배송 담당자 생성 시작. userId: {}, type: {}", request.deliveryManagerId(), request.type());
        // 외부 User 서비스: USER 도메인에서 배송 담당자 타입 검증
        userFacade.validateDeliveryManagerRole(request.deliveryManagerId());
        // 도메인 서비스: 중복 검증
        domainService.validateNotDuplicate(request.deliveryManagerId());
        // Hub ID 생성
        HubId hubId = createHubId(request.type(), request.hubId());
        // 도메인 서비스: 순번 계산
        Integer nextSequence = domainService.calculateNextSequence(request.type(), hubId);
        // 배송 담당자 생성
        DeliveryManager manager = DeliveryManager.create(
                request.deliveryManagerId(),
                hubId,
                SlackId.of(request.slackId()),
                request.type(),
                nextSequence
        );
        DeliveryManager saved = repository.save(manager);
        log.info("배송 담당자 생성 완료. id: {}, sequence: {}", saved.getDeliveryManagerId(), saved.getDeliverySequence());

        return CreateDeliveryManagerResponse.from(saved);
    }

    /**
     * 배송 담당자 단건 조회
     */
    public DeliveryManagerResponse getDeliveryManager(
            Long deliveryManagerId,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        DeliveryManager manager = findDeliveryManagerById(deliveryManagerId);
        validateReadPermission(manager, currentUserId, currentUserRole);
        return DeliveryManagerResponse.from(manager);
    }

    /**
     * HubId 생성
     */
    private HubId createHubId(DeliveryManagerType type, UUID hubId) {
        if (type == DeliveryManagerType.COMPANY_DELIVERY) {
            // 외부 Hub 서비스: Hub 도메인에서 존재 검증
            hubFacade.validateHubExists(hubId);
            return HubId.of(hubId);
        }
        return null;
    }

    /**
     * ID로 배송 담당자 조회
     */
    private DeliveryManager findDeliveryManagerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.DELIVERY_MANAGER_NOT_FOUND));
    }

    /**
     * 배송 담당자 조회 권한 (마스터, 허브관리자, 배송담당자)
     */
    private void validateReadPermission(
            DeliveryManager manager,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        if (currentUserRole == UserRole.MASTER_ADMIN) {
            return;
        }

        if (currentUserRole == UserRole.HUB_MANAGER) {
            UUID hubId = hubFacade.getUserHubId(currentUserId);

            if (!manager.belongsToHub(HubId.of(hubId))) {
                throw new BusinessException(ErrorCode.HUB_MANAGER_FORBIDDEN);
            }
            return;
        }

        if (currentUserRole == UserRole.DELIVERY_MANAGER) {
            if (!manager.getDeliveryManagerId().equals(currentUserId)) {
                throw new BusinessException(ErrorCode.DELIVERY_MANAGER_SELF_ONLY);
            }
            return;
        }

        throw new BusinessException(ErrorCode.USER_ROLE_UNAUTHORIZED);
    }
}
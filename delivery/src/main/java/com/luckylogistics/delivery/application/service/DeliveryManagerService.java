package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerResponse;
import com.luckylogistics.delivery.application.facade.HubFacade;
import com.luckylogistics.delivery.application.facade.UserFacade;
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
}
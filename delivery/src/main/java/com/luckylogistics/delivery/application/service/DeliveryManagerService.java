package com.luckylogistics.delivery.application.service;

import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerRequest;
import com.luckylogistics.delivery.application.dto.CreateDeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.DeliveryManagerResponse;
import com.luckylogistics.delivery.application.dto.UpdateDeliveryManagerRequest;
import com.luckylogistics.delivery.common.enums.UserRole;
import com.luckylogistics.delivery.common.exception.BusinessException;
import com.luckylogistics.delivery.common.exception.ErrorCode;
import com.luckylogistics.delivery.common.util.PageableUtils;
import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import com.luckylogistics.delivery.domain.model.SlackId;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import com.luckylogistics.delivery.domain.service.DeliveryDomainService;
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
public class DeliveryManagerService {

    private final DeliveryManagerRepository repository;
    private final DeliveryDomainService domainService;
    private final UserService userService;
    private final HubService hubService;

    /**
     * 배송 담당자 생성
     */
    @Transactional
    public CreateDeliveryManagerResponse createDeliveryManager(
            CreateDeliveryManagerRequest request
    ) {
        log.info("[DeliveryManager] 배송 담당자 생성 시작. userId: {}, type: {}", request.deliveryManagerId(), request.type());
        // 외부 User 서비스: USER 도메인에서 배송 담당자 타입 검증
        userService.validateDeliveryManagerRole(request.deliveryManagerId());
        // 도메인 서비스: 중복 검증
        domainService.validateNotDuplicate(request.deliveryManagerId());
        // Hub ID 검증
        UUID hubId = validateHubId(request.type(), request.hubId());
        // 도메인 서비스: 순번 계산
        Integer nextSequence = domainService.calculateNextSequence(request.type(), hubId);
        // 배송 담당자 생성
        DeliveryManager manager = DeliveryManager.create(
                request.deliveryManagerId(),
                hubId,
                SlackId.of(request.slackId()),
                request.type(),
                nextSequence,
                request.startTime(),
                request.endTime()
        );
        DeliveryManager saved = repository.save(manager);
        log.info("[DeliveryManager] 배송 담당자 생성 완료. id: {}, sequence: {}", saved.getDeliveryManagerId(), saved.getDeliverySequence());

        return CreateDeliveryManagerResponse.from(saved);
    }

    /**
     * Hub ID 검증 및 반환
     */
    private UUID validateHubId(DeliveryManagerType type, UUID hubId) {
        if (type.isCompanyDelivery()) {
            // 외부 Hub 서비스: Hub 도메인에서 존재 검증
            hubService.validateHubExists(hubId);
            return hubId;
        }
        return null;
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
        if (currentUserRole.isMaster()) {
            return;
        }

        if (currentUserRole.isHubManager()) {
            UUID hubId = hubService.getUserHubId(currentUserId);

            if (!manager.belongsToHub(hubId)) {
                throw new BusinessException(ErrorCode.HUB_MANAGER_FORBIDDEN);
            }
            return;
        }

        if (currentUserRole.isDeliveryManager()) {
            if (!manager.getDeliveryManagerId().equals(currentUserId)) {
                throw new BusinessException(ErrorCode.DELIVERY_MANAGER_SELF_ONLY);
            }
            return;
        }

        throw new BusinessException(ErrorCode.USER_ROLE_UNAUTHORIZED);
    }

    /**
     * 배송 담당자 수정
     */
    @Transactional
    public DeliveryManagerResponse updateDeliveryManager(
            Long deliveryManagerId,
            UpdateDeliveryManagerRequest request,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        log.info("[DeliveryManager] 배송 담당자 수정 시작. id: {}, type: {}", deliveryManagerId, request.type());
        // 조회
        DeliveryManager manager = findDeliveryManagerById(deliveryManagerId);
        // 권한 검증
        validateWritePermission(manager, currentUserId, currentUserRole);
        // Hub ID 검증
        UUID newHubId = validateHubId(request.type(), request.hubId());
        // 새 순번 결정
        Integer newSequence = determineSequenceForUpdate(manager, request.type(), newHubId);
        // 배송 담당자 수정
        manager.update(newHubId, SlackId.of(request.slackId()), request.type(), request.startTime(), request.endTime());

        // 필요시, 순서 재배정
        if (newSequence != null) {
            manager.reassignSequence(newSequence);
            log.info("[DeliveryManager] 시퀀스 재배정 완료 id={}, newSeq={}", deliveryManagerId, newSequence);
        }

        log.info("[DeliveryManager] 배송 담당자 수정 완료. id: {}", deliveryManagerId);
        return DeliveryManagerResponse.from(manager);
    }

    /**
     * 배송 담당자 수정/삭제 권한 (마스터, 허브관리자)
     */
    private void validateWritePermission(
            DeliveryManager manager,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        if (currentUserRole.isMaster()) {
            return;
        }

        if (currentUserRole.isHubManager()) {
            UUID hubId = hubService.getUserHubId(currentUserId);

            if (!manager.belongsToHub(hubId)) {
                throw new BusinessException(ErrorCode.HUB_MANAGER_FORBIDDEN);
            }

            return;
        }

        throw new BusinessException(ErrorCode.USER_ROLE_UNAUTHORIZED);
    }

    /**
     * 타입/허브 변경에 따른 순서 재배정
     * - HUB_DELIVERY: 전체 허브 대상 전역 시퀀스(max + 1)
     * - COMPANY_DELIVERY: 특정 허브 내 시퀀스(max + 1)
     * - 변경 없으면 기존 유지(null)
     */
    private Integer determineSequenceForUpdate(
            DeliveryManager manager,
            DeliveryManagerType newType,
            UUID newHubId
    ) {
        // 재배정 불필요 - 기존 순번 유지
        if (!manager.requiresSequenceReassignment(newType, newHubId)) {
            return null;
        }

        // 새 순번 계산
        int nextSeq = domainService.calculateNextSequence(newType, newHubId);
        log.info("[DeliveryManager] 시퀀스 재배정 필요: prevType={}, newType={}, newHubId={}, newSeq={}",
                manager.getType(), newType, newHubId, nextSeq);

        return nextSeq;
    }

    /**
     * 배송 담당자 삭제
     */
    @Transactional
    public void deleteDeliveryManager(
            Long deliveryManagerId,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        log.info("[DeliveryManager] 배송 담당자 삭제 시작. id: {}", deliveryManagerId);

        DeliveryManager manager = findDeliveryManagerById(deliveryManagerId);
        validateWritePermission(manager, currentUserId, currentUserRole);
        manager.delete(currentUserId);

        log.info("[DeliveryManager] 배송 담당자 삭제 완료. id: {}", deliveryManagerId);
    }

    /**
     * 배송 담당자 목록 조회
     */
    public Page<DeliveryManagerResponse> getDeliveryManagers(
            DeliveryManagerType type,
            UUID hubId,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            Long currentUserId,
            UserRole currentUserRole
    ) {
        Pageable pageable = PageableUtils.createPageable(page, size, sortBy, direction);
        Page<DeliveryManager> managers = findDeliveryManagers(
                type, hubId, currentUserId, currentUserRole, pageable);

        return managers.map(DeliveryManagerResponse::from);
    }

    /**
     * 조건에 따른 배송 담당자 목록 조회
     */
    public Page<DeliveryManager> findDeliveryManagers(
            DeliveryManagerType requestType,
            UUID requestHubId,
            Long currentUserId,
            UserRole currentUserRole,
            Pageable pageable
    ) {
        // 권한 검증
        if (!currentUserRole.isMaster() && !currentUserRole.isHubManager()) {
            throw new BusinessException(ErrorCode.USER_ROLE_UNAUTHORIZED);
        }

        // 마스터 관리자
        if (currentUserRole.isMaster()) {
            // HUB_DELIVERY: hubId는 항상 null (전체 조회)
            if (requestType.isHubDelivery()) {
                return repository.findByTypeAndHubId(DeliveryManagerType.HUB_DELIVERY, null, pageable);
            }
            // COMPANY_DELIVERY: hubId 필터 유/무 모두 허용 (허브별/전체조회)
            return repository.findByTypeAndHubId(requestType, requestHubId, pageable);
        }

        // 허브 관리자
        UUID myHubId = hubService.getUserHubId(currentUserId);
        if (myHubId == null) {
            throw new BusinessException(ErrorCode.USER_HUB_NOT_FOUND);
        }

        // 다른 허브 접근 불가 (담당 허브만 가능)
        if (requestHubId != null && !Objects.equals(requestHubId, myHubId)) {
            throw new BusinessException(ErrorCode.HUB_MANAGER_FORBIDDEN);
        }

        // 타입이 null이면 COMPANY_DELIVERY로 강제
        DeliveryManagerType effectiveType =
                (requestType == null) ? DeliveryManagerType.COMPANY_DELIVERY : requestType;

        if (effectiveType != DeliveryManagerType.COMPANY_DELIVERY) {
            throw new BusinessException(ErrorCode.HUB_MANAGER_FORBIDDEN);
        }

        return repository.findByTypeAndHubId(requestType, myHubId, pageable);
    }
}
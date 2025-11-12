package com.luckylogistics.delivery.domain.service;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import com.luckylogistics.delivery.domain.repository.DeliveryRepository;
import com.luckylogistics.delivery.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 배송 담당자 도메인 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryDomainService {

    private final DeliveryManagerRepository managerRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteRepository routeRepository;


    /**
     * 다음 배송 순번 계산
     * - HUB_DELIVERY: 전체 허브 배송 담당자 중 최대값 + 1
     * - COMPANY_DELIVERY: 특정 허브의 업체 배송 담당자 중 최대값 + 1
     * - 0부터 시작
     */
    public Integer calculateNextSequence(DeliveryManagerType type, UUID hubId) {
        if (type == DeliveryManagerType.HUB_DELIVERY) {
            return managerRepository.findMaxSequenceByType(type)
                    .map(maxSeq -> maxSeq + 1)
                    .orElse(0);
        } else {
            if (hubId == null) {
                throw new IllegalArgumentException(
                        "업체 배송 담당자는 Hub ID가 필요합니다");
            }
            return managerRepository.findMaxSequenceByTypeAndHubId(type, hubId)
                    .map(maxSeq -> maxSeq + 1)
                    .orElse(0);
        }
    }

    /**
     * 배송 담당자 중복 검증
     * - 한 사용자는 하나의 배송 담당자만 등록 가능
     */
    public void validateNotDuplicate(Long deliveryManagerId) {
        if (managerRepository.existsById(deliveryManagerId)) {
            throw new IllegalStateException(
                    "이미 배송 담당자로 등록된 사용자입니다. deliveryManagerId: " + deliveryManagerId);
        }
    }

    /**
     * 업체 배송 담당자 순차 배정
     * - 해당 허브의 활성 업체 배송 담당자 목록 조회 (deliverySequence 오름차순)
     * - 해당 허브의 마지막 배송 조회
     * - 마지막 배송의 담당자 다음 순번 담당자 배정
     * - 마지막 배송이 없으면 첫 번째 담당자 배정
     */
    public DeliveryManager assignCompanyDeliveryManager(UUID hubId) {
        // 활성 담당자 목록 조회
        List<DeliveryManager> managers = managerRepository.findCompanyDeliveryManagersByHubId(hubId);

        if (managers.isEmpty()) {
            throw new IllegalArgumentException("해당 허브에 업체 배송 담당자가 없습니다");
        }

        // 마지막 배정된 담당자 찾기
        DeliveryManager lastAssignedManager = findLastAssignedCompanyManager(hubId, managers);

        // 다음 순번 담당자 선택
        return selectNextManager(managers, lastAssignedManager);
    }

    /**
     * 허브 배송 담당자 순차 배정
     * - 전체 허브 배송 담당자 목록 조회 (deliverySequence 오름차순)
     * - 마지막 배송 경로 조회
     * - 마지막 배송 경로의 담당자 다음 순번 담당자 배정
     * - 마지막 배송 경로가 없으면 첫 번째 담당자 배정
     */
    public DeliveryManager assignHubDeliveryManager() {

        // 활성 담당자 목록 조회
        List<DeliveryManager> managers = managerRepository.findHubDeliveryManagers();

        if (managers.isEmpty()) {
            throw new IllegalArgumentException("허브 배송 담당자가 없습니다");
        }

        // 마지막 배정된 담당자 찾기
        DeliveryManager lastAssignedManager = findLastAssignedHubManager(managers);

        // 다음 순번 담당자 선택
        return selectNextManager(managers, lastAssignedManager);
    }

    /**
     * 마지막 배정된 업체 배송 담당자 찾기
     */
    private DeliveryManager findLastAssignedCompanyManager(UUID hubId, List<DeliveryManager> managers) {
        return deliveryRepository.findLastDeliveryByArrivalHubId(hubId)
                .map(Delivery::getCompanyDeliveryManager)
                .filter(managers::contains)
                .orElse(null);
    }

    /**
     * 마지막 배정된 허브 배송 담당자 찾기
     */
    private DeliveryManager findLastAssignedHubManager(List<DeliveryManager> managers) {
        return routeRepository.findLastDeliveryRoute()
                .map(DeliveryRoute::getHubDeliveryManager)
                .filter(managers::contains)
                .orElse(null);
    }

    /**
     * 다음 담당자 선택 (Round-Robin)
     *
     * @param managers 활성 담당자 목록 (deliverySequence 오름차순 정렬)
     * @param lastAssigned 마지막 배정된 담당자 (null이면 첫 번째 담당자 반환)
     * @return 다음 배정할 담당자
     */
    private DeliveryManager selectNextManager(List<DeliveryManager> managers, DeliveryManager lastAssigned) {
        if (lastAssigned == null) {
            // 첫 배정이거나 마지막 담당자가 삭제된 경우
            return managers.get(0);
        }

        // 마지막 담당자의 인덱스 찾기
        int lastIndex = managers.indexOf(lastAssigned);

        if (lastIndex == -1) {
            // 마지막 담당자가 리스트에 없으면 (삭제됨) 첫 번째 담당자
            return managers.get(0);
        }

        // 다음 인덱스 계산 (Round-Robin)
        int nextIndex = (lastIndex + 1) % managers.size();
        return managers.get(nextIndex);
    }
}
package com.luckylogistics.delivery.domain.service;

import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import com.luckylogistics.delivery.domain.vo.HubId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 배송 담당자 도메인 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryDomainService {

    private final DeliveryManagerRepository repository;

    /**
     * 다음 배송 순번 계산
     * - HUB_DELIVERY: 전체 허브 배송 담당자 중 최대값 + 1
     * - COMPANY_DELIVERY: 특정 허브의 업체 배송 담당자 중 최대값 + 1
     * - 0부터 시작
     */
    public Integer calculateNextSequence(DeliveryManagerType type, HubId hubId) {
        if (type == DeliveryManagerType.HUB_DELIVERY) {
            return repository.findMaxSequenceByType(type)
                    .map(maxSeq -> maxSeq + 1)
                    .orElse(0);
        } else {
            if (hubId == null) {
                throw new IllegalArgumentException(
                        "업체 배송 담당자는 Hub ID가 필요합니다");
            }
            return repository.findMaxSequenceByTypeAndHubId(type, hubId.getHubId())
                    .map(maxSeq -> maxSeq + 1)
                    .orElse(0);
        }
    }

    /**
     * 배송 담당자 중복 검증
     * - 한 사용자는 하나의 배송 담당자만 등록 가능
     */
    public void validateNotDuplicate(Long deliveryManagerId) {
        if (repository.existsById(deliveryManagerId)) {
            throw new IllegalStateException(
                    "이미 배송 담당자로 등록된 사용자입니다. deliveryManagerId: " + deliveryManagerId);
        }
    }
}
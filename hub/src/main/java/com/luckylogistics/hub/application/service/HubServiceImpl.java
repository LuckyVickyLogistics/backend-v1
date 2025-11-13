package com.luckylogistics.hub.application.service;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.hub.application.dto.*;
import com.luckylogistics.hub.application.exception.HubNotFoundException;
import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;

    @Override
    public HubCreateResponse createHub(HubCreateRequest request, Long userId, UserRole currentUserRole) {

        if(!currentUserRole.isMaster()){
            throw new IllegalArgumentException("허가되지 않은 접근입니다.");
        }
        Hub hub = Hub.create(
                request.name(),
                request.address(),
                request.latitude(),
                request.longitude()
        );

        Hub saved = hubRepository.save(hub);

        return new HubCreateResponse(
                saved.getHubId(),
                saved.getName(),
                saved.getAddress(),
                saved.getLatitude(),
                saved.getLongitude()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<HubResponse> getAllHubs() {
        return hubRepository.findAllActive()
                .stream()
                .map(this::toHubResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HubResponse getHub(UUID hubId) {
        Hub hub = hubRepository.findActiveById(hubId)
                .orElseThrow(() -> new HubNotFoundException("존재하지 않거나 삭제된 허브입니다."));

        return toHubResponse(hub);
    }

    @Override
    public HubResponse updateHub(UUID hubId, HubUpdateRequest request, Long userId, UserRole currentUserRole) {

        if(!currentUserRole.isMaster()){
            throw new IllegalArgumentException("허가되지 않은 접근입니다.");
        }

        Hub hub = hubRepository.findActiveById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 허브입니다."));

        hub.update(
                request.name(),
                request.address(),
                request.latitude(),
                request.longitude(),
                userId
        );
        return toHubResponse(hub);
    }

    @Override
    public void deleteHub(UUID hubId, Long userId, UserRole currentUserRole) {

        if(!currentUserRole.isMaster()){
            throw new IllegalArgumentException("허가되지 않은 접근입니다.");
        }

        Hub hub = hubRepository.findActiveById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 허브입니다."));

        hub.softDelete(userId);
        // 역시 변경 감지로 업데이트됨 (필요하면 hubRepository.save(hub) 호출해도 됨)
    }

    private HubResponse toHubResponse(Hub hub) {
        return new HubResponse(
                hub.getHubId(),
                hub.getName(),
                hub.getAddress(),
                hub.getLatitude(),
                hub.getLongitude(),
                hub.getCreatedAt(),
                hub.getUpdatedAt()
        );
    }
}

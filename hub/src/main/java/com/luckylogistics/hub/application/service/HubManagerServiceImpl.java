package com.luckylogistics.hub.application.service;

import com.luckylogistics.hub.application.dto.*;
import com.luckylogistics.hub.application.exception.HubNotFoundException;
import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.model.HubManager;
import com.luckylogistics.hub.domain.repository.HubManagerRepository;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubManagerServiceImpl implements HubManagerService {

    private final HubManagerRepository repository;
    private final HubRepository hubRepository;

    @Override
    public HubManagerCreateResponse createHubManager(HubManagerCreateRequest request, Long userId) {
        HubManager hubManager = HubManager.create(
                request.userId(),
                request.name(),
                request.hubId(),
                request.email()
        );

        HubManager saved = repository.save(hubManager);

        return new HubManagerCreateResponse(
                saved.getHubManagerId(),
                saved.getUserId(),
                saved.getName(),
                saved.getHubId(),
                saved.getEmail()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<HubManagerResponse> getAllHubManagers() {
        return repository.findAllActive().stream().map(this::toHubManagerResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HubManagerResponse getHubManager(UUID hubId) {
        HubManager hubManager = repository.findActiveByHubId(hubId)
                .orElseThrow(() -> new HubNotFoundException("존재하지 않거나 삭제된 허브입니다."));

        return toHubManagerResponse(hubManager);
    }

    @Override
    public HubResponse getHubByUserId(Long userId) {
        HubManager hubManager = repository.findActiveByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 아이디입니다."));

        Hub hub = hubRepository.findActiveById(hubManager.getHubId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 허브입니다."));
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

    @Override
    public HubManagerResponse updateHubManager(UUID hubManagerId, HubManagerUpdateRequest request, Long userId) {
        HubManager hubManager = repository.findActiveByManagerId(hubManagerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 매니저입니다."));

        hubManager.update(
                request.userId(),
                request.name(),
                request.email(),
                userId
        );
        return toHubManagerResponse(hubManager);
    }

    @Override
    public void deleteHubManager(UUID hubManagerId, Long userId) {
        HubManager hubManager = repository.findActiveByManagerId(hubManagerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 매니저입니다."));
        hubManager.delete(userId);
    }

    private HubManagerResponse toHubManagerResponse(HubManager hubManager) {
        return new HubManagerResponse(
                hubManager.getHubManagerId(),
                hubManager.getUserId(),
                hubManager.getName(),
                hubManager.getHubId(),
                hubManager.getEmail(),
                hubManager.getCreatedAt(),
                hubManager.getUpdatedAt()
        );
    }
}

package com.luckylogistics.hub.application.service;

import java.util.List;
import java.util.UUID;

import com.luckylogistics.hub.application.dto.HubDTO;
import com.luckylogistics.hub.application.dto.hubCreateCommand;
import com.luckylogistics.hub.application.dto.hubCreateResult;
import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.repository.HubRepository;
import com.luckylogistics.hub.domain.service.HubDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubRepository hubRepository;
    private final HubDomainService hubDomainService;

    @Override
    public hubCreateResult create(hubCreateCommand command) {
        Hub hub = dto.toDomain();
        hub = Hub.builder()
                .hubId(UUID.randomUUID().toString())
                .name(hub.getName())
                .address(hub.getAddress())
                .location(hub.getLocation())
                .build();
        hub.markCreated(username);

        hubDomainService.validateCreatable(hub);
        return HubDTO.from(hubRepository.save(hub));
    }

    @Override
    public HubDTO get(String hubId) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("허브를 찾을 수 없습니다."));
        return HubDTO.from(hub);
    }

    @Override
    public List<HubDTO> list(int page, int size) {
        return hubRepository.findAll(page, size).stream().map(HubDTO::from).toList();
    }

    @Override
    public HubDTO update(String hubId, HubDTO dto, String username) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("허브를 찾을 수 없습니다."));
        hub.update(dto.getName(), dto.getAddress(),
                hub.getLocation().of(dto.getLatitude(), dto.getLongitude()), username);
        return HubDTO.from(hubRepository.save(hub));
    }

    @Override
    public void deleteSoft(String hubId, String username) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new IllegalArgumentException("허브를 찾을 수 없습니다."));
        hub.delete();
        hubRepository.save(hub);
    }
}
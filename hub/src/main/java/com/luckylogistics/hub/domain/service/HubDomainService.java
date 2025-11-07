package com.luckylogistics.hub.domain.service;

import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HubDomainService {
    private final HubRepository hubRepository;

    public void validateCreatable(Hub hub) {
        hubRepository.findByAddress(hub.getAddress())
                .ifPresent(h -> { throw new IllegalArgumentException("이미 존재하는 주소입니다."); });
    }
}
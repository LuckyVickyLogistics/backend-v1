package com.luckylogistics.hub.application.service;

import com.luckylogistics.hub.application.dto.HubDTO;

import java.util.List;

public interface HubService {
    HubDTO create(HubDTO dto, String username);
    HubDTO get(String hubId);
    List<HubDTO> list(int page, int size);
    HubDTO update(String hubId, HubDTO dto, String username);
    void deleteSoft(String hubId, String username);
}
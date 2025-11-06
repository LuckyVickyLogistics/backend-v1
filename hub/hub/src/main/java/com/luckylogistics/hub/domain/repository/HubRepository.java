package com.luckylogistics.hub.domain.repository;

import com.luckylogistics.hub.domain.model.Hub;

import java.util.*;

public interface HubRepository {
    Hub save(Hub hub);
    Optional<Hub> findById(String hubId);
    Optional<Hub> findByAddress(String address);
    List<Hub> findAll(int page, int size);
    void deleteHard(String hubId); // 필요 시 하드삭제
}
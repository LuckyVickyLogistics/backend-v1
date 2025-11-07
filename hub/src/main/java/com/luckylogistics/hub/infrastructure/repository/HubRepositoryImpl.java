package com.luckylogistics.hub.infrastructure.repository;

import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.model.Location;
import com.luckylogistics.hub.domain.repository.HubRepository;
import com.luckylogistics.hub.infrastructure.entity.HubJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final JpaHubRepository jpa;

    private Hub toDomain(HubJpaEntity e) {
        if (e == null) return null;
        Hub hub = Hub.builder()
                .hubId(e.getHubId())
                .name(e.getName())
                .address(e.getAddress())
                .location(Location.of(e.getLatitude(), e.getLongitude()))
                .build();
        // BaseEntity 필드 복사
        hub.setCreatedAt(e.getCreatedAt());
        hub.setCreatedBy(e.getCreatedBy());
        hub.setUpdatedAt(e.getUpdatedAt());
        hub.setUpdatedBy(e.getUpdatedBy());
        hub.setDeletedAt(e.getDeletedAt());
        hub.setDeletedBy(e.getDeletedBy());
        hub.setDeleted(e.isDeleted());
        return hub;
    }

    private HubJpaEntity toEntity(Hub d) {
        HubJpaEntity e = new HubJpaEntity();
        e.setHubId(d.getHubId());
        e.setName(d.getName());
        e.setAddress(d.getAddress());
        e.setLatitude(d.getLocation() != null ? d.getLocation().getLatitude() : null);
        e.setLongitude(d.getLocation() != null ? d.getLocation().getLongitude() : null);
        // BaseEntity 필드 복사
        e.setCreatedAt(d.getCreatedAt());
        e.setCreatedBy(d.getCreatedBy());
        e.setUpdatedAt(d.getUpdatedAt());
        e.setUpdatedBy(d.getUpdatedBy());
        e.setDeletedAt(d.getDeletedAt());
        e.setDeletedBy(d.getDeletedBy());
        e.setDeleted(d.isDeleted());
        return e;
    }

    @Override
    public Hub save(Hub hub) {
        return toDomain(jpa.save(toEntity(hub)));
    }

    @Override
    public Optional<Hub> findById(String hubId) {
        return jpa.findById(hubId).map(this::toDomain);
    }

    @Override
    public Optional<Hub> findByAddress(String address) {
        return jpa.findByAddress(address).map(this::toDomain);
    }

    @Override
    public List<Hub> findAll(int page, int size) {
        return jpa.findAll(org.springframework.data.domain.PageRequest.of(page, size))
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteHard(String hubId) {
        jpa.deleteById(hubId);
    }
}

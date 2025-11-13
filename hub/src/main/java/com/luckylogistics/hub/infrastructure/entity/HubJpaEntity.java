package com.luckylogistics.hub.infrastructure.entity;

import com.luckylogistics.hub.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "p_hub", indexes = {
        @Index(name = "ux_hub_address", columnList = "address", unique = true)
})
@Getter @Setter
public class HubJpaEntity extends BaseEntity {

    @Id
    @Column(name = "hub_id", length = 100)
    private UUID hubId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "address", length = 255, nullable = false, unique = true)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
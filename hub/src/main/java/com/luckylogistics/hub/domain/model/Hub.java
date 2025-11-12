package com.luckylogistics.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_hub")
public class Hub extends BaseEntity {

    @Id
    @UuidGenerator   // Hibernate 6+
    @Column(name = "hub_id")
    private UUID hubId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "address", length = 255, nullable = false, unique = true)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // 팀 컨벤션: 엔티티 팩토리
    public static Hub create(String name, String address, Double latitude, Double longitude) {
        validate(name, address, latitude, longitude);
        Hub hub = new Hub();
        hub.name = name.trim();
        hub.address = address.trim();
        hub.latitude = latitude;
        hub.longitude = longitude;
        return hub;
    }

    private static void validate(String name, String address, Double lat, Double lon) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("허브 이름은 필수입니다.");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("주소는 필수입니다.");
        if (lat != null && (lat < -90 || lat > 90)) throw new IllegalArgumentException("위도 범위 오류");
        if (lon != null && (lon < -180 || lon > 180)) throw new IllegalArgumentException("경도 범위 오류");
    }

    public void update(String name, String address, Double latitude, Double longitude, Long updatedBy) {
        validate(name, address, latitude, longitude);
        this.name = name.trim();
        this.address = address.trim();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void softDelete(Long deletedBy) {
        delete(deletedBy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hub)) return false;
        return Objects.equals(hubId, ((Hub) o).hubId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(hubId);
    }
}

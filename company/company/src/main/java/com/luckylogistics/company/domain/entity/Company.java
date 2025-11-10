package com.luckylogistics.company.domain.entity;

import com.luckylogistics.company.infrastructure.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "type", nullable = false, length = 10)
    private CompanyType type;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    public static Company create(String name, String address, CompanyType type, UUID hubId) {
        validate(name, address, type);
        return Company.builder()
            .name(name)
            .address(address)
            .type(type)
            .hubId(hubId)
            .build();
    }

    public void update(String name, String address, CompanyType type, UUID hubId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.hubId = hubId;
    }

    public static void validate(String name, String address, CompanyType type) {
        if (name == null || name.isBlank()){ throw new IllegalArgumentException("Company name cannot be blank"); }
        if (address == null || address.isBlank()){ throw new IllegalArgumentException("Company address cannot be blank"); }
        if (type == null) { throw new IllegalArgumentException("Company type cannot be null"); }
    }
}

package com.luckylogistics.company.domain.entity;

import com.luckylogistics.company.infrastructure.model.BaseEntity;
import com.luckylogistics.company.application.dto.CompanyRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address; // todo: VO 객체로 전환 가능

    @Column(name = "type", nullable = false, length = 10)
    private CompanyType type;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Builder
    public Company(String name, String address, CompanyType type, UUID hubId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.hubId = hubId;
    }

    public void update(String name, String address, CompanyType type, UUID hubId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.hubId = hubId;
    }
}

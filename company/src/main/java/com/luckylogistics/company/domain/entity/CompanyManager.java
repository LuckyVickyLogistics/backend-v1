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
@Table(name = "p_company_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyManager extends BaseEntity {
    @Id
    @Column(name = "manager_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID managerId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    public static CompanyManager create(Long userId, UUID companyId) {
        validate(userId, companyId);
        return CompanyManager.builder()
            .userId(userId)
            .companyId(companyId)
            .build();
    }

    private static void validate(Long userId, UUID companyId) {
        if (userId == null){ throw new IllegalArgumentException("유저 아이디는 필수 입력 값입니다"); }
        if (companyId == null) { throw new IllegalArgumentException("업체 아이디는 필수 입력 값입니다"); }
    }


}

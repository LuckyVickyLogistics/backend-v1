package com.luckylogistics.product.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

//외부 company MSA에서 전달되는 UUID를 만드는 VO
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyId {
    // private UUID companyId;
    private UUID value;
}

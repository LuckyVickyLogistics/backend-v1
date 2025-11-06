package com.luckylogistics.delivery.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Hub ID 값 객체
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId {

    private UUID hubId;

    private HubId(UUID hubId) {
        validate(hubId);
        this.hubId = hubId;
    }

    public static HubId of(UUID hubId) {
        return new HubId(hubId);
    }

    private void validate(UUID hubId) {
        if (hubId == null) {
            throw new IllegalArgumentException("Hub ID는 필수입니다");
        }
    }
}
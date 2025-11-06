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
        this.hubId = hubId;
    }

    // null 허용 (허브 담당자는 hubId가 없음)
    public static HubId of(UUID hubId) {
        return hubId == null ? null : new HubId(hubId);
    }
}
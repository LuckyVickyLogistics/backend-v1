package com.luckylogistics.hub.application.dto;

import com.luckylogistics.hub.domain.model.Hub;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class HubDTO {
    private String hubId;   // 외부 노출은 문자열
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static HubDTO from(Hub hub) {
        return HubDTO.builder()
                .hubId(hub.getHubId() != null ? hub.getHubId().toString() : null)
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getLatitude())
                .longitude(hub.getLongitude())
                .build();
    }
}

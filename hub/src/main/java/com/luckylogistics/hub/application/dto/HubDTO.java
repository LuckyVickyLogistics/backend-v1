package com.luckylogistics.hub.application.dto;

import com.luckylogistics.hub.domain.model.Hub;
import com.luckylogistics.hub.domain.model.Location;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class HubDTO {
    private String hubId;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    public static HubDTO from(Hub hub) {
        return HubDTO.builder()
                .hubId(hub.getHubId())
                .name(hub.getName())
                .address(hub.getAddress())
                .latitude(hub.getLocation() != null ? hub.getLocation().getLatitude() : null)
                .longitude(hub.getLocation() != null ? hub.getLocation().getLongitude() : null)
                .build();
    }

    public Hub toDomain() {
        return Hub.builder()
                .hubId(hubId)
                .name(name)
                .address(address)
                .location(Location.of(latitude, longitude))
                .build();
    }
}
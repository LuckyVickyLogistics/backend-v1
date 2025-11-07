package com.luckylogistics.hub.domain.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    private Double latitude;
    private Double longitude;

    public static Location of(Double lat, Double lon) {
        return new Location(lat, lon);
    }
}
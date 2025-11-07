package com.luckylogistics.hub.domain.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hub extends BaseEntity {
    private String hubId;     // UUID
    private String name;      // not null
    private String address;   // unique
    private Location location;
}

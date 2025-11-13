package com.luckylogistics.hub.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(schema = "hubs", name = "p_hub_route")
public class HubConnection extends BaseEntity{

    @Id
    @UuidGenerator
    @Column(name = "route_id")
    private UUID routeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_hub_id", nullable = false)
    private Hub fromHub;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_hub_id", nullable = false)
    private Hub toHub;

    @Column(name = "time", nullable = false)
    private int time;

    @Column(name = "distance", nullable = false)
    private double distance;

    private HubConnection(Hub fromHub, Hub toHub, int time, double distance) {
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.time = time;
        this.distance = distance;
    }

    public static void of(Hub fromHub, Hub toHub, int time, double distance) {
        if (distance <= 0) throw new IllegalArgumentException("거리는 0 보다 커야합니다.");
        if (time <= 0) throw new IllegalArgumentException("시간은 0보다 커야합니다.");
    }

    public static HubConnection create(Hub fromHub, Hub toHub, int time, double distance) {
        of(fromHub, toHub, time, distance);
        HubConnection hc = new HubConnection();
        hc.fromHub = fromHub;
        hc.toHub = toHub;
        hc.time = time;
        hc.distance = distance;
        return hc;
    }

    public void update(Hub fromHub, Hub toHub, int time, double distance) {
        of(fromHub, toHub, time, distance);
        this.fromHub = fromHub;
        this.toHub = toHub;
        this.time = time;
        this.distance = distance;
    }

    public void softDelete(Long deletedBy){ delete(deletedBy); }

    public UUID fromId() {return fromHub.getHubId();}
    public UUID toId() {return toHub.getHubId();}
}

package com.luckylogistics.hub.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(schema = "hubs", name = "p_hub_manager")
public class HubManager extends BaseEntity{

    @Id
    @UuidGenerator
    @Column(name = "hub_manager_id")
    private UUID hubManagerId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Email
    @Column(name = "slack_id", nullable = false)
    private String email;

    public static HubManager create(Long userId, String name, UUID hubId, String email) {
        HubManager hubManager = new HubManager();
        hubManager.userId = userId;
        hubManager.name = name;
        hubManager.hubId = hubId;
        hubManager.email = email;
        return hubManager;
    }

    public void update(Long userId, String name, String email, Long updatedBy) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public void softDelete(Long deletedBy) { delete(deletedBy); }
}

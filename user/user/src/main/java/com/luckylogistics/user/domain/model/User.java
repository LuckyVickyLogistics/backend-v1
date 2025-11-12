package com.luckylogistics.user.domain.model;

import java.util.UUID;

import com.luckylogistics.user.application.dto.SignupCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_users", schema = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private Long userId;

	@Column(name = "identifier",nullable = false, unique = true, columnDefinition = "uuid")
	private UUID identifier;

	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private UserRole role;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "slack_id", nullable = false)
	private String slackId;

	@Enumerated(EnumType.STRING)
	@Column(name = "organization_type")
	private OrganizationType organizationType;

	@Column(name = "organization_id")
	private UUID organizationId;

	//@Column(name = "organization_name")
	//private String organizationName;

	public static User createPendingUser(SignupCommand command) {
		return User.builder()
			.username(command.username())
			.password(command.password())
			.status(Status.PENDING)
			.slackId(command.slackId())
			.organizationType(command.organizationType())
			.organizationId(command.organizationId())
			//.organizationName(command.organizationName())
			.build();
	}

	@PrePersist
	protected void prePersist() {
		if (this.identifier == null) {
			this.identifier = UUID.randomUUID();
		}
	}

	public void approve() {
		this.status = Status.APPROVED;
	}

	public void reject() {
		this.status = Status.REJECTED;
	}
}

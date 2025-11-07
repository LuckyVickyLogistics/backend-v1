package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * Slack ID 값 객체
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SlackId {

    // 이메일 형식
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Column(name = "slack_id", nullable = false, length = 100)
    private String slackId;

    private SlackId(String slackId) {
        validate(slackId);
        this.slackId = slackId;
    }

    public static SlackId of(String slackId) {
        return new SlackId(slackId);
    }

    private void validate(String slackId) {
        if (slackId == null || slackId.isBlank()) {
            throw new IllegalArgumentException("Slack ID는 필수입니다");
        }
        if (!EMAIL_PATTERN.matcher(slackId).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식의 Slack ID가 아닙니다: " + slackId);
        }
    }
}

package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

/**
 * 수령인 정보 값 객체
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipient {

    // 이메일 형식
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final int MAX_NAME_LENGTH = 100;

    @Column(name = "recipient_name", nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(name = "recipient_slack_id", nullable = false, length = 100)
    private String slackId;

    private Recipient(String name, String slackId) {
        validateName(name);
        validateSlackId(slackId);
        this.name = name;
        this.slackId = slackId;
    }

    public static Recipient of(String name, String slackId) {
        return new Recipient(name, slackId);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("수령인 이름은 필수입니다");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("수령인 이름은 %d자 이하여야 합니다", MAX_NAME_LENGTH));
        }
    }

    private static void validateSlackId(String slackId) {
        if (slackId == null || slackId.isBlank()) {
            throw new IllegalArgumentException("수령인 슬랙 ID는 필수입니다");
        }
        if (!EMAIL_PATTERN.matcher(slackId).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식의 Slack ID가 아닙니다: " + slackId);
        }
    }
}
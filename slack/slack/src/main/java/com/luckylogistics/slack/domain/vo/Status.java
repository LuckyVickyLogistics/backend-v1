package com.luckylogistics.slack.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

	PENDING("준비") {
		@Override
		public boolean canTransitionTo(Status newStatus) {
			return newStatus == SUCCESS || newStatus == RETRY;
		}
	},

	SUCCESS("성공") {
		@Override
		public boolean canTransitionTo(Status newStatus) {
			return false;
		}
	},

	RETRY("재시도") {
		@Override
		public boolean canTransitionTo(Status newStatus) {
			return newStatus == SUCCESS || newStatus == FAILED;
		}
	},

	FAILED("실패") {
		@Override
		public boolean canTransitionTo(Status newStatus) {
			return false;
		}
	};

	private final String description;

	public abstract boolean canTransitionTo(Status newStatus);

}

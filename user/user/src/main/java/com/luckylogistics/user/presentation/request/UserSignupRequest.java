package com.luckylogistics.user.presentation.request;

import com.luckylogistics.user.domain.model.OrganizationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserSignupRequest(

	@NotBlank(message = "아이디는 필수 입력값입니다.")
	@Pattern(regexp = "^[a-z0-9]{4,10}$",
			message = "아이디는 소문자 영문과 숫자를 포함한 4~10자로 입력해야 합니다.")
	String username,

	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*])[A-Za-z\\d!@#$%&*]{8,15}$",
			message = "비밀번호는 소문자와 대문자 영문, 숫자, 특수문자를 포함한 8~15자로 입력해야 합니다.")
	String password,

	@NotBlank(message = "슬랙 아이디는 필수 입력값입니다.")
	@Email(message = "올바른 이메일 형식이어야 합니다.")
	String slackId,

	@NotNull(message = "소속된 곳의 유형(HUB 또는 COMPANY)이 필요합니다.")
	OrganizationType organizationType,

	@NotNull(message = "소속된 곳의 이름이 필요합니다.")
	String organizationName
) {}

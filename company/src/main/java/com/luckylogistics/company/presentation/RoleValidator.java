package com.luckylogistics.company.presentation;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {
    public void validate(UserRole userRole, UserRole... roles) {
        boolean authorized =  Arrays.stream(roles)
            .anyMatch(role -> role.equals(userRole));

        if (!authorized) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_USER_ROLE);
        }
    }
}

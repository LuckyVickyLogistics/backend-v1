package com.luckylogistics.company.presentation;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {
    public void validate(UserRole userRole, String... roles) {
        boolean authorized =  Arrays.stream(roles)
            .anyMatch(role -> role.equalsIgnoreCase(userRole));

        if (!authorized) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_USER_ROLE);
        }
    }

    public final class Roles {
        public static final String MASTER_ADMIN = "MASTER_ADMIN";
        public static final String HUB_MANAGER = "HUB_MANAGER";
        public static final String DELIVERY_MANAGER = "DELIVERY_MANAGER";
        public static final String COMPANY_MANAGER = "COMPANY_MANAGER";
    }
}

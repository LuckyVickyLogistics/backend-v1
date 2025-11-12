package com.luckylogistics.company.presentation;

<<<<<<< HEAD:company/src/main/java/com/luckylogistics/company/presentation/RoleValidator.java
import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
=======
import com.luckylogistics.company.common.exception.BusinessException;
import com.luckylogistics.company.common.exception.ErrorCode;
>>>>>>> 0142e986fcb15532a6e616cfb2de124f068f7952:company/company/src/main/java/com/luckylogistics/company/presentation/RoleValidator.java
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class RoleValidator {
    public void validate(String userRole, String... roles) {
        boolean authorized =  Arrays.stream(roles)
            .anyMatch(role -> role.equalsIgnoreCase(userRole));

        if (!authorized) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_USER_ROLE);
        }
<<<<<<< HEAD:company/src/main/java/com/luckylogistics/company/presentation/RoleValidator.java
=======


>>>>>>> 0142e986fcb15532a6e616cfb2de124f068f7952:company/company/src/main/java/com/luckylogistics/company/presentation/RoleValidator.java
    }

    public final class Roles {
        public static final String MASTER_ADMIN = "MASTER_ADMIN";
        public static final String HUB_MANAGER = "HUB_MANAGER";
        public static final String DELIVERY_MANAGER = "DELIVERY_MANAGER";
        public static final String COMPANY_MANAGER = "COMPANY_MANAGER";
    }
}

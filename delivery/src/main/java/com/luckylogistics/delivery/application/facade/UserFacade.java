package com.luckylogistics.delivery.application.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 외부 User 서비스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {

    // TODO: 외부 서비스 클라이언트 주입
    // private final UserClient userClient;

    /**
     * 배송 담당자 권한 검증
     */
    public void validateDeliveryManagerRole(Long userId) {
        // TODO: User Service 연동
        // UserResponse user = userClient.getUser(userId);
        // if (user.role() != UserRole.DELIVERY_MANAGER) {
        //     throw new BusinessException(ErrorCode.INVALID_USER_ROLE);
        // }

        log.warn("[TODO] User Service 연동 필요 - userId 검증 생략: {}", userId);
    }
}

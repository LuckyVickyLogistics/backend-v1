package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.service.UserClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientService {
    // TODO: 외부 서비스 클라이언트 주입
    // private final UseFeignClient userClient;

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

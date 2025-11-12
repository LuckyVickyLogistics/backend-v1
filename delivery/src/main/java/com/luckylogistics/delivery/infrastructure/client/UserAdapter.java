package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.service.UserService;
import com.luckylogistics.delivery.infrastructure.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdapter implements UserService {

     private final UseFeignClient userClient;

    // 배송 담당자 권한 검증
    @Override
    public void validateUserIsDeliveryManager(Long userId) {
        UserResponse response = userClient.getUser(userId).data();
        response.validate();
        response.validateDeliveryManagerRole();
        log.debug("[UserClient] 배송 담당자 권한 검증 완료. userId={}, role={}", userId, response.role());
    }
}

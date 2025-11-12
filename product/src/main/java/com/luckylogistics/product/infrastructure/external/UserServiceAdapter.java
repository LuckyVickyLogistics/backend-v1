package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.application.external.UserService;
import com.luckylogistics.product.common.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.UserFeignClient;
import com.luckylogistics.product.infrastructure.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {
    private final UserFeignClient userFeignClient;
    @Override
    public UserResponse getUser(Long userId) {
        ApiResponse<UserResponse> response = userFeignClient.getUser(userId);
        return response.data();
    }
}

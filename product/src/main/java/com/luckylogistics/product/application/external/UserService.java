package com.luckylogistics.product.application.external;

import com.luckylogistics.product.common.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.dto.UserResponse;


public interface UserService {
    UserResponse getUser(Long userId);
}

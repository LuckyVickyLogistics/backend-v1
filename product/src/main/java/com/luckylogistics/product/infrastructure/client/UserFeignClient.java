package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: User Service 연동
@FeignClient(name = "user")
public interface UserFeignClient {

    @GetMapping("/api/v1/hubs/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId);
}

package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", path = "/api/v1/users")
public interface UseFeignClient {

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId);
}
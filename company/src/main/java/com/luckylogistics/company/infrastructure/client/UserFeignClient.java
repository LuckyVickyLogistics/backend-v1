package com.luckylogistics.company.infrastructure.client;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.company.infrastructure.client.dto.UserDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserFeignClient {
    @GetMapping("/api/v1/users/{userId}")
    ApiResponse<UserDetailResponse> getUserById(@PathVariable Long userId);
}
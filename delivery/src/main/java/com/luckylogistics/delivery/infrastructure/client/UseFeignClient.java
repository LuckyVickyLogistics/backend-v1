package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.infrastructure.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: User Service 연동
@FeignClient(name = "user")
public interface UseFeignClient {

    @GetMapping("/api/v1/users/{userId}")
    UserResponse getUser(@PathVariable("userId") Long userId);
}
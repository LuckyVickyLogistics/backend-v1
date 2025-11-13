package com.luckylogistics.company.infrastructure.external;

import com.luckylogistics.company.application.external.UserService;
import com.luckylogistics.company.infrastructure.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {
    private final UserFeignClient userFeignClient;

    @Override
    public void isUserExists(Long userId){
        userFeignClient.getUserById(userId);
    }

}

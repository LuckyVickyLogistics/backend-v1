package com.luckylogistics.order.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 API" , description = "주문 관련 API입니다.")
public class OrderController {
}

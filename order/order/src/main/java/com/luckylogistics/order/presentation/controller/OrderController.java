package com.luckylogistics.order.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.order.application.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 API" , description = "주문 관련 API입니다.")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성", description = "주문 생성 api")
	@PostMapping
	public ResponseEntity<Void> createOrder() {
		orderService.createOrder();
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}

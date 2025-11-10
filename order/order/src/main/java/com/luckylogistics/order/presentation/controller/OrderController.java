package com.luckylogistics.order.presentation.controller;

import com.luckylogistics.order.application.dto.OrderUpdateRequest;
import com.luckylogistics.order.application.dto.OrderUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luckylogistics.order.application.service.OrderService;

import java.util.UUID;

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



    @Operation(summary = "주문 수정", description = "주문의 수량과 요청을 수정합니다.")
    @PutMapping("/{orderid}")
    public ResponseEntity<OrderUpdateResponse> updateOrder(@Valid @PathVariable("orderid") UUID orderid, OrderUpdateRequest orderUpdateRequest) {
        OrderUpdateResponse result= orderService.updateOrder(orderid,orderUpdateRequest);
        return ResponseEntity.ok(result);
    }

    //주문을 삭제할 경우 재고를 되돌리는가? ->
    @Operation(summary ="주문 삭제", description = "주문을 삭제합니다.")
    @PatchMapping("/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(@Valid @PathVariable("orderId") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(true);
    }
    //주문을 롤백할 경우, 재고를 되돌리는가? 만약에 되돌린 개수가 총 개수를 초과한다면?
    @Operation(summary ="주문 삭제 롤백", description = "삭제한 주문을 되돌립니다.")
    @PatchMapping("/rollbackOrders/{orderId}")
    public ResponseEntity<Boolean> rollbackOrders(@Valid @PathVariable("orderId") UUID orderId) {
        orderService.rollbackDeleteOrder(orderId);
        return ResponseEntity.ok(true);
    }



}

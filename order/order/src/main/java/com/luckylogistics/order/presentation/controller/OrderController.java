package com.luckylogistics.order.presentation.controller;

import com.luckylogistics.order.application.dto.OrderResponse;
import com.luckylogistics.order.application.dto.OrderUpdateRequest;
import com.luckylogistics.order.application.dto.OrderUpdateResponse;
import com.luckylogistics.order.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luckylogistics.order.application.service.OrderService;

import java.util.List;
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


    //FeignClient의 orderId 체크하는 메소드 추가


    //전체 조회, 추후에 권한 추가 필요 (user는 본인, master 는 전부 볼수 있음)
    @Operation(summary = "주문 전체 조회", description = "전체 주문을 조회한다")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
       List<OrderResponse> list = orderService.getAllOrders();
       return ResponseEntity.ok(ApiResponse.success(list,"주문 조회 결과입니다."));
    }

    //주문 단건을 조회함
    @Operation(summary ="주문 단건 조회", description = "주문 단건을 조회한다.")
    @GetMapping("/searchOne/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable("orderId") UUID orderId){
        OrderResponse result = OrderResponse.from(orderService.getOrderById(orderId));
        return ResponseEntity.ok(ApiResponse.success(result,"주문 단건 조회 결과입니다."));
    }


    @Operation(summary = "주문 수정", description = "주문의 수량과 요청을 수정합니다.")
    @PutMapping("/{orderid}")
    public ResponseEntity<ApiResponse<OrderUpdateResponse>> updateOrder(@Valid @PathVariable("orderid") UUID orderid, OrderUpdateRequest orderUpdateRequest) {
        OrderUpdateResponse result= orderService.updateOrder(orderid,orderUpdateRequest);
        return ResponseEntity.ok(ApiResponse.success(result,"주문 수정이 완료되었습니다."));
    }

    //주문을 삭제할 경우 재고를 되돌리는가? -> (o)
    @Operation(summary ="주문 삭제", description = "주문을 삭제합니다.")
    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrder(@Valid @PathVariable("orderId") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(null,"주문 삭제가 완료되었습니다."));
    }
    //주문을 롤백할 경우, 재고를 되돌리는가? (o)
    @Operation(summary ="주문 삭제 롤백", description = "삭제한 주문을 되돌립니다.")
    @PatchMapping("/rollbackOrders/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> rollbackOrders(@Valid @PathVariable("orderId") UUID orderId) {
        orderService.rollbackDeleteOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(null,"주문 롤백이 완료되었습니다."));
    }



}

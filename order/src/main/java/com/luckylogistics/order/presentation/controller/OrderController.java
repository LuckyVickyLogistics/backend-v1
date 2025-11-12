package com.luckylogistics.order.presentation.controller;

import java.util.List;
import java.util.UUID;

import com.luckylogistics.order.common.enums.UserRole;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luckylogistics.order.application.dto.OrderRequest;
import com.luckylogistics.order.application.dto.OrderResponse;
import com.luckylogistics.order.application.dto.OrderUpdateRequest;
import com.luckylogistics.order.application.dto.OrderUpdateResponse;
import com.luckylogistics.order.application.service.OrderService;
import com.luckylogistics.order.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 API" , description = "주문 관련 API입니다.")
public class OrderController {

	private final OrderService orderService;

	@Operation(summary = "주문 생성", description = "주문을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createOrder(
            @Valid @RequestBody OrderRequest requestDto) {
		orderService.createOrder(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("주문이 생성되었습니다."));
	}


//    //FeignClient의 orderId 체크하는 메소드 추가
//    @Operation(summary = "FeignClient 조회 메서드", description = "FeginClient 주문 체크용")
//    @GetMapping("/{orderId}")
//    public ResponseEntity<ApiResponse<Boolean>> isOrderIdExists(@PathVariable(name = "orderId") UUID orderId){
//        Boolean result = orderService.checkOrder(orderId);
//        return ResponseEntity.ok(ApiResponse.success(result,"FeignClient: 주문 정상 조회 되었습니다."));
//    }


    //전체 조회, 추후에 권한 추가 필요 (user는 본인, master 는 전부 볼수 있음)
    @Operation(summary = "주문 전체 조회", description = "전체 주문을 조회한다")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders(
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
       List<OrderResponse> list = orderService.getAllOrders(currentUserId,currentUserRole);
       return ResponseEntity.ok(ApiResponse.success(list,"주문 조회 결과입니다."));
    }

    //주문 단건을 조회함
    @Operation(summary ="주문 단건 조회", description = "주문 단건을 조회한다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
            @PathVariable("orderId") UUID orderId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole){
        OrderResponse result = OrderResponse.from(orderService.getOrderById(orderId,currentUserId,currentUserRole));
        return ResponseEntity.ok(ApiResponse.success(result,"주문 단건 조회 결과입니다."));
    }


    @Operation(summary = "주문 수정", description = "주문의 수량과 요청을 수정합니다.")
    @PutMapping("/{orderid}")
    public ResponseEntity<ApiResponse<OrderUpdateResponse>> updateOrder(
            @Valid @PathVariable("orderid") UUID orderid,
            OrderUpdateRequest orderUpdateRequest,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        OrderUpdateResponse result= orderService.updateOrder(orderid,orderUpdateRequest,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(result,"주문 수정이 완료되었습니다."));
    }

    //주문을 삭제할 경우 재고를 되돌리는가? -> (o)
    @Operation(summary ="주문 삭제", description = "주문을 삭제합니다.")
    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrder(
            @Valid @PathVariable("orderId") UUID orderId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        orderService.deleteOrder(orderId,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"주문 삭제가 완료되었습니다."));
    }
    //주문을 롤백할 경우, 재고를 되돌리는가? (o)
    @Operation(summary ="주문 삭제 롤백", description = "삭제한 주문을 되돌립니다.")
    @PatchMapping("/rollbackOrders/{orderId}")
    public ResponseEntity<ApiResponse<Boolean>> rollbackOrders(
            @Valid @PathVariable("orderId") UUID orderId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        orderService.rollbackDeleteOrder(orderId,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"주문 롤백이 완료되었습니다."));
    }



}

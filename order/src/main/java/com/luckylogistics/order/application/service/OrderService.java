package com.luckylogistics.order.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.luckylogistics.order.application.dto.*;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.order.domain.entity.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.order.application.event.OrderKafkaEventPublisher;
import com.luckylogistics.order.application.external.CompanyService;
import com.luckylogistics.order.application.external.DeliveryService;
import com.luckylogistics.order.application.external.HubService;
import com.luckylogistics.order.application.external.ProductService;
import com.luckylogistics.order.application.external.UserService;
import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import com.luckylogistics.order.domain.entity.Order;
import com.luckylogistics.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final CompanyService companyService;
    private final DeliveryService deliveryService;
    private final HubService hubService;
    private final OrderKafkaEventPublisher kafkaEventPublisher;

    @Transactional
    public void createOrder(OrderRequest requestDto) {
        // 상품 조회
        ProductResponse productResponse = validateAndGetProduct(requestDto.productId(), requestDto.quantity());

        // 로그인한 사람의 username, slackId, 소속 업체 ID 조회
        UserResponse userResponse = getUserInfo();

        // 주문 생성
        Order order = Order.create(
                requestDto.quantity(),
                productResponse.departureCompanyId(), // 공급업체 ID
                userResponse.companyId(),// 주문한 사용자 소속 업체 ID (수령업체)
                requestDto.productId(),
                requestDto.deliveryAddress(),
                requestDto.request()
        );
        orderRepository.save(order);

        // 상품 재고 차감
        decreaseProductStock(requestDto);

        // 업체 담당 허브 (출발허브) 조회
        CompanyHubResponse companyHubResponse = getCompanyHub(userResponse.companyId());

        // 배송 생성
        // TODO: 배송 경로에서 포함된 ID를 통해 허브 서비스에서 허브의 이름을 받아와야함
        DeliveryCreateResponse deliveryCreateResponse = createDelivery(order, productResponse, companyHubResponse, userResponse);
        order.updateDeliveryToOrder(order.getOrderId(), deliveryCreateResponse.deliveryId());

        // 출발 허브 관리자 슬랙 이메일 조회
        HubManagerEmailResponse hubManagerEmailResponse = getHubManagerEmail(companyHubResponse.arrivalHubId());

        // 카프카 이벤트 발송
        publishEvent(order, productResponse, userResponse, deliveryCreateResponse, hubManagerEmailResponse);
    }

    private ProductResponse validateAndGetProduct(UUID productId, int quantity) {
        ProductResponse response = productService.getProductById(productId);
        if (response.quantity() < quantity) {
            throw new BusinessException(ErrorCode.ORDER_QUANTITY_EXCEEDS_STOCK);
        }
        return response;
    }

    private UserResponse getUserInfo() {
        return userService.getUserCompany(); // 수령 업체 ID가 들어 있음
    }

    private void decreaseProductStock(OrderRequest requestDto) {
        productService.minusProduct(requestDto.productId(), new MinusRequest(requestDto.quantity()));
    }

    // 업체 담당 허브 (출발허브) 조회
    private CompanyHubResponse getCompanyHub(UUID companyId) {
        return companyService.getCompanyHub(companyId);
    }

    private DeliveryCreateResponse createDelivery(Order order, ProductResponse productResponse, CompanyHubResponse companyHubResponse, UserResponse userResponse) {
        return deliveryService.createDelivery(
                order, productResponse.departureHubId(), companyHubResponse.arrivalHubId(), userResponse.username(), userResponse.slackId()
        );
    }

    private HubManagerEmailResponse getHubManagerEmail(UUID hubId) {
        return hubService.getHubManagerEmail(hubId);
    }

    private void publishEvent(
            Order order, ProductResponse productResponse, UserResponse userResponse,
            DeliveryCreateResponse deliveryCreateResponse, HubManagerEmailResponse hubManagerEmailResponse
    ) {
        kafkaEventPublisher.publish(
                order.getOrderId(), userResponse.username(), userResponse.slackId(), order.getCreatedAt(),
                productResponse.productName(), productResponse.quantity(), order.getRequest(),
                deliveryCreateResponse.startPoint(), deliveryCreateResponse.waypoints(), deliveryCreateResponse.endPoint(),
                deliveryCreateResponse.deliveryManagerName(), deliveryCreateResponse.deliveryManagerSlackId(),
                deliveryCreateResponse.deliveryManagerWorkingStartTime(), deliveryCreateResponse.deliveryManagerWorkingEndTime(),
                hubManagerEmailResponse.slackId()
        );
    }

    //update
    @Transactional
    public OrderUpdateResponse updateOrder(UUID orderId, OrderUpdateRequest orderUpdateRequest, Long currentUserId, UserRole currentUserRole) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_ID_ERROR));

        //권한 확인
        if (currentUserRole.isDeliveryManager() || currentUserRole.isCompanyManager()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        //수령받는 업체 ID
        UUID hubId = order.getDeliveryId();

        //수령받는 업체가 아니면 throw한다.
        if (currentUserRole.isHubManager()) {
            //로그인한 사람의 hubId
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!hubId.equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
        }

        //주문 변경 시 재고의 변경을 감지해야 함
        int before = order.getQuantity();
        int after = orderUpdateRequest.Quantity();
        int difference = after - before;
        //수량이 증가할 경우 그 만큼 재고를 감소시키고, 아니라면 추가한다
        if (difference > 0) {
            productService.minusProduct(order.getProductId(), new MinusRequest(difference));
        } else if (difference < 0) {
            //절댓값 사용해야 함 (0보다 작으면 빠지니까)
            productService.plusProduct(order.getProductId(), new PlusRequest(Math.abs(difference)));
        }

        order.update(after, orderUpdateRequest.request());

        return new OrderUpdateResponse(
                order.getOrderId(),
                order.getQuantity(),
                order.getRequest()
        );

    }

//    //FeignClient용
//    public Boolean checkOrder(UUID orderId) {
//        return orderRepository.findById(orderId).isPresent();
//    }

    //전체 조회
    public List<OrderResponse> getAllOrders(Long currentUserId, UserRole currentUserRole) {
        if (currentUserRole.isMaster()) {
            List<Order> orders = orderRepository.findAll();
            return orders.stream().map(OrderResponse::from).toList();
        }
        //currentUserId를 통해 허브를 찾아야함, 허브 관리자 체크해서 JPA
        //1. 담당허브 관리자가 아니라면 본인거만 조회해야함,
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            //1 담당허브 관리자일 경우
            List<Order> hubOrders = orderRepository.findAllByDeliveryId(userHubId);
            //2. 아닐 경우
            List<Order> myOrders = orderRepository.findAllByCreatedBy(currentUserId.toString());
            //3. 합치는건 gpt로 물어봤어요..
            List<Order> combinedOrders = Stream.concat(hubOrders.stream(), myOrders.stream())
                    .distinct()
                    .toList();

            return combinedOrders.stream().map(OrderResponse::from).toList();
        }

        //본인것만 찾기
        List<Order> orders = orderRepository.findAllByCreatedBy(currentUserId.toString());
        return orders.stream().map(OrderResponse::from).toList();
    }

    //단건 조회
    public Order getOrderById(UUID orderId, Long currentUserId, UserRole currentUserRole) {
        //Master
        if (currentUserRole.isMaster()) {
            orderRepository.findById(orderId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_ID_ERROR));
        }
        //HubManager일 때
        //담당허브 관리자가 아니라면 본인거만 조회해야함,

        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();

            return orderRepository.findByOrderIdAndDeliveryId(orderId, userHubId) //1. 본인이 허브관리자
                    //본인이 허브 관리자 아닐때
                    .or(() -> orderRepository.findByOrderIdAndCreatedBy(orderId, currentUserId.toString()))
                    .orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN));
        }

        //본인꺼만 보기..
        return orderRepository.findByOrderIdAndCreatedBy(orderId, currentUserId.toString())
                .orElseThrow(() -> new BusinessException(ErrorCode.FORBIDDEN));


    }

    //delete
    @Transactional
    public void deleteOrder(UUID orderId, Long currentUserId, UserRole currentUserRole) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_ID_ERROR));

        if (order.getStatus() == OrderStatus.DELETED) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_DELETED);
        }
        if (currentUserRole.isMaster()) {
            //가진만큼 재고에 더해야함.
            productService.plusProduct(order.getProductId(), new PlusRequest(order.getQuantity()));
            //이후 삭제
            order.delete();
            return;
        }
        //담당 허브만 삭제 가능!
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (order.getDeliveryId().equals(userHubId)) {
                productService.plusProduct(order.getProductId(), new PlusRequest(order.getQuantity()));
                order.delete();
                return;
            }
        }

        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    //rollback
    @Transactional
    public void rollbackDeleteOrder(UUID orderId, Long currentUserId, UserRole currentUserRole) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_ID_ERROR));
        if (order.getStatus() == OrderStatus.ORDERED) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_EXIST);
        }
        if (currentUserRole.isMaster()) {
            //가진만큼 재고에서 빼야함 (롤백)
            productService.minusProduct(order.getProductId(), new MinusRequest(order.getQuantity()));
            //이후 삭제
            order.rollbackDelete();
            return;
        }
        //담당 허브만 삭제 가능!
        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (order.getDeliveryId().equals(userHubId)) {
                productService.minusProduct(order.getProductId(), new MinusRequest(order.getQuantity()));
                order.rollbackDelete();
                return;
            }
        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }
}

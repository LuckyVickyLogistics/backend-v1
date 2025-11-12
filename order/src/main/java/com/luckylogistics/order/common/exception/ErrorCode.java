package com.luckylogistics.order.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //공통(E)
    DOMAIN_ERROR(HttpStatus.BAD_REQUEST, "D-001", "도메인 규칙을 위반했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "D-002", "잘못된 입력값입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "D-003", "권한이 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "D-004", "잘못된 요청입니다."),
    INVALID_HEADER_USER_ROLE(HttpStatus.BAD_REQUEST, "D-005", "잘못된 X-User-Role 헤더입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "D-006", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "D-999", "서버 오류가 발생했습니다."),

    //상품 관련 에러 (P01~...)
    PRODUCT_CANNOT_FIND(HttpStatus.NOT_FOUND, "P-001", "해당 상품을 찾을 수 없습니다."),
    PRODUCT_READ_FAIL(HttpStatus.NOT_FOUND, "P-002", "상품 조회에 실패했습니다."),
    PRODUCT_NAME_ERROR(HttpStatus.BAD_REQUEST, "P-003", "상품의 이름은 빌 수 없습니다."),
    PRODUCT_PRICE_ZERO(HttpStatus.BAD_REQUEST, "P-004", "상품 가격은 0원 이상이어야 합니다."),
    PRODUCT_HUB_ERROR(HttpStatus.BAD_REQUEST, "P-005", "허브 ID는 필수값입니다."),
    PRODUCT_COMPANY_ERROR(HttpStatus.BAD_REQUEST, "P-006", "업체 ID는 필수값입니다."),
    PRODUCT_QUANTITY_NONZERO(HttpStatus.BAD_REQUEST, "P-007", "총 수량은 0 이상이어야 합니다."),
    PRODUCT_TOTAL_EXCEED(HttpStatus.BAD_REQUEST, "P-008", "초기 재고로 지정된 값이 총 수량을 초과했습니다."),
    PRODUCT_NAME_UPDATE(HttpStatus.BAD_REQUEST, "P-009", "수정할 상품의 이름은 빌 수 없습니다."),
    PRODUCT_PRICE_UPDATE(HttpStatus.BAD_REQUEST, "P-010", "수정할 상품 가격은 0원 이상이어야 합니다."),
    PRODUCT_QUANTITY_ZERO_UPDATE(HttpStatus.BAD_REQUEST, "P-011", "수정할 수량은 0 이상의 숫자여야 합니다."),
    PRODUCT_QUANTITY_TOTAL_UPDATE(HttpStatus.BAD_REQUEST, "P-012", "수정한 재고 수가 총 수량을 초과했습니다."),
    PRODUCT_QUANTITY_DELETED(HttpStatus.GONE, "P-013", "삭제된 상품의 재고는 차감될 수 없습니다."),
    QUANTITY_MINUS_EXCEED(HttpStatus.BAD_REQUEST, "P-014", "상품 재고가 부족합니다."),
    QUANTITY_MINUS_NONZERO(HttpStatus.BAD_REQUEST, "P-015", "출고할 재고를 1개 이상 선택해야 합니다."),
    QUANTITY_AMOUNT_ERROR(HttpStatus.BAD_REQUEST, "P-016", "추가 및 삭제하려고 하는 상품 개수는 총 수량을 넘길 수 없습니다."),
    QUANTITY_PLUS_EXCEED(HttpStatus.BAD_REQUEST, "P-017", "추가한 수량의 합이 총 수량을 초과합니다."),
    QUANTITY_PLUS_NONZERO(HttpStatus.BAD_REQUEST, "P-018", "추가할 재고를 1개 이상 선택해야 합니다."),

    //ORDER (OR01)
    ORDER_SUPPLY_ERROR(HttpStatus.NOT_FOUND, "OR-001", "해당 업체를 찾을 수 없습니다."),
    ORDER_QUANTITY_ERROR(HttpStatus.BAD_REQUEST, "OR-002", "주문 수량은 1개 이상이어야 합니다."),
    ORDER_CUSTOMER_ERROR(HttpStatus.NOT_FOUND, "OR-003", "고객 업체를 찾을 수 없습니다. 다시 입력해주세요."),
    ORDER_PRODUCT_ERROR(HttpStatus.NOT_FOUND, "OR-004", "해당 상품을 찾을 수 없습니다. 다시 입력해주세요."),
    ORDER_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "OR-005", "요청사항이 제대로 입력되지 않았습니다."),
    ORDER_DELIVERY_ERROR(HttpStatus.NOT_FOUND, "OR-006", "배송 ID를 찾지 못했습니다."),
    ORDER_ID_ERROR(HttpStatus.NOT_FOUND, "OR-007", "주문 ID를 찾지 못했습니다."),
    ORDER_ALREADY_DELETED(HttpStatus.GONE, "OR-008", "삭제된 주문입니다."),
    ORDER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "OR-009", "이미 주문이 존재합니다."),
    ORDER_QUANTITY_EXCEEDS_STOCK(HttpStatus.BAD_REQUEST, "OR-010", "상품의 총 재고보다 많이 주문할 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

}

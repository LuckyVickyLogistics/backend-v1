package com.luckylogistics.order.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //공통(E)
    INTERNAL_SERVER_ERROR("E01", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT("E02", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("E03", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("E04", "접근이 거부되었습니다.", HttpStatus.FORBIDDEN),
    METHOD_NOT_ALLOWED("E05", "지원하지 않는 API 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    DATA_INTEGRITY_VIOLATION("E06", "데이터 무결성 제약 조건을 위반했습니다.", HttpStatus.CONFLICT),
    UNSUPPORTED_MEDIA_TYPE("E07", "지원하지 않는 요청 형식입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    //상품 관련 에러 (P01~...)
    PRODUCT_CANNOT_FIND("P-01","해당 상품을 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
    PRODUCT_READ_FAIL("P-02", "상품 조회에 실패했습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_NAME_ERROR("P-03", "상품의 이름은 빌 수 없습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_ZERO("P-04", "상품 가격은 0원 이상이어야 합니다",HttpStatus.BAD_REQUEST),
    PRODUCT_HUB_ERROR("P-05", "허브 ID는 필수값입니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_COMPANY_ERROR("P-06", "업체 ID는 필수값입니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_NONZERO("P-07", "총 수량은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_TOTAL_EXCEED("P-08", "초기 재고로 지정된 값이 총 수량을 초과했습니다", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_UPDATE("P-09", "수정할 상품의 이름은 빌 수 없습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_UPDATE("P-10", "수정할 상품 가격은 0원 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_ZERO_UPDATE("P-11", "수정할 수량은 0 이상의 숫자여야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_TOTAL_UPDATE("P-12", "수정한 재고 수가 총 수량을 초과했습니다.",  HttpStatus.BAD_REQUEST ),
    PRODUCT_QUANTITY_DELETED("P-13", "삭제된 상품의 재고는 차감될 수 없습니다.", HttpStatus.GONE),
    QUANTITY_MINUS_EXCEED("P-14", "상품 재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    QUANTITY_MINUS_NONZERO("P-15", "출고할 재고를 1개 이상 선택해야 합니다", HttpStatus.BAD_REQUEST),
    QUANTITY_AMOUNT_ERROR("P-16", "추가 및 삭제하려고 하는 상품 개수는 총 수량을 넘길 수 없습니다",HttpStatus.BAD_REQUEST),
    QUANTITY_PLUS_EXCEED("P-17", "추가한 수량의 합이 총 수량을 초과합니다.", HttpStatus.BAD_REQUEST),
    QUANTITY_PLUS_NONZERO("P-18", "추가할 재고를 1개 이상 선택해야 합니다",HttpStatus.BAD_REQUEST),

    //ORDER (OR01)
    ORDER_SUPPLY_ERROR("OR-1", "해당 업체를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_QUANTITY_ERROR("OR-2", "주문 수량은 1개 이상이어야 합니다.",HttpStatus.BAD_REQUEST),
    ORDER_CUSTOMER_ERROR("OR-3", "고객 업체를 찾을 수 없습니다. 다시 입력해주세요", HttpStatus.NOT_FOUND),
    ORDER_PRODUCT_ERROR("OR-4", "해당 상품을 찾을 수 없습니다. 다시 입력해주세요",HttpStatus.NOT_FOUND),
    ORDER_REQUEST_ERROR("OR-5","요청사항이 제대로 입력되지 않았습니다.",HttpStatus.NOT_FOUND),
    ORDER_DELIVERY_ERROR("OR-6", "배송 ID를 찾지 못했습니다", HttpStatus.NOT_FOUND),
    ORDER_ID_ERROR("OR-7", "주문ID를 찾지 못했습니다." ,HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

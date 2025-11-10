package com.luckylogistics.product.common.exception;

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
    PRODUCT_CANNOT_FIND("P01","해당 상품을 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
    PRODUCT_READ_FAIL("P02", "상품 조회에 실패했습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_NAME_ERROR("P03", "상품의 이름은 빌 수 없습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_ZERO("P04", "상품 가격은 0원 이상이어야 합니다",HttpStatus.BAD_REQUEST),
    PRODUCT_HUB_ERROR("P05", "허브 ID는 필수값입니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_COMPANY_ERROR("P06", "업체 ID는 필수값입니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_NONZERO("P07", "총 수량은 0 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_TOTAL_EXCEED("P08", "초기 재고로 지정된 값이 총 수량을 초과했습니다", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_UPDATE("P09", "수정할 상품의 이름은 빌 수 없습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_UPDATE("P10", "수정할 상품 가격은 0원 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_ZERO_UPDATE("P11", "수정할 수량은 0 이상의 숫자여야 합니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_TOTAL_UPDATE("P12", "수정한 재고 수가 총 수량을 초과했습니다.",  HttpStatus.BAD_REQUEST ),
    PRODUCT_QUANTITY_DELETED("P13", "삭제된 상품의 재고는 차감될 수 없습니다.", HttpStatus.GONE),
    QUANTITY_MINUS_EXCEED("P14", "상품 재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    QUANTITY_MINUS_NONZERO("P15", "출고할 재고를 1개 이상 선택해야 합니다", HttpStatus.BAD_REQUEST),
    QUANTITY_AMOUNT_ERROR("P16", "추가 및 삭제하려고 하는 상품 개수는 총 수량을 넘길 수 없습니다",HttpStatus.BAD_REQUEST),
    QUANTITY_PLUS_EXCEED("P17", "추가한 수량의 합이 총 수량을 초과합니다.", HttpStatus.BAD_REQUEST),
    QUANTITY_PLUS_NONZERO("P18", "추가할 재고를 1개 이상 선택해야 합니다",HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}

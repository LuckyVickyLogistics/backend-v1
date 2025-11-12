package com.luckylogistics.company.domain.entity;

import com.luckylogistics.company.common.exception.BusinessException;
import com.luckylogistics.company.common.exception.ErrorCode;

public enum CompanyType {
    SUPPLIER, CUSTOMER;

    public static void validateCompanyType(CompanyType type) {
        if (type != SUPPLIER && type != CUSTOMER) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}

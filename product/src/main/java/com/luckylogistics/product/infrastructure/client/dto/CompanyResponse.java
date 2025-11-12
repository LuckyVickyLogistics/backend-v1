package com.luckylogistics.product.infrastructure.client.dto;

import java.util.UUID;

public record CompanyResponse(
        UUID companyId,
        String name,
        String address,
        UUID hubId,
        Long userId

) {

}
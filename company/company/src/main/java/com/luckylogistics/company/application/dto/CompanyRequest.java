package com.luckylogistics.company.application.dto;

import com.luckylogistics.company.domain.entity.CompanyType;
import java.util.UUID;

public record CompanyRequest (
    String name,
    String address,
    CompanyType type,
    UUID hubId
){
}

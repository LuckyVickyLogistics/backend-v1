package com.luckylogistics.company.application.dto;

import com.luckylogistics.company.domain.entity.Company;
import com.luckylogistics.company.domain.entity.CompanyType;
import java.util.UUID;

public record CompanyRequest (
    String name,
    String address,
    CompanyType type,
    UUID hubId
){
    public Company toEntity(){
        return Company.builder()
            .name(name)
            .address(address)
            .type(type)
            .hubId(hubId)
            .build();
    }
}

package com.luckylogistics.company.application.dto;

import com.luckylogistics.company.domain.entity.CompanyType;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;


public record CompanyRequest (
    @NotNull String name,
    @NotNull String address,
    @NotNull CompanyType type,
    @NotNull UUID hubId
){
}

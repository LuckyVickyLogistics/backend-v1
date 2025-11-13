package com.luckylogistics.company.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CompanyManagerRequest(
    @NotNull Long useId,
    @NotNull UUID companyId
) {

}

package com.luckylogistics.company.presentation;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.company.application.CompanyManagerService;
import com.luckylogistics.company.application.dto.CompanyManagerRequest;
import com.luckylogistics.company.application.dto.CompanyManagerResponse;
import com.luckylogistics.company.application.dto.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company-managers")
@Tag(name = "업체 관리자 API" , description = "업체 관리자 관련 API입니다.")
public class CompanyManagerController {
    private final CompanyManagerService companyManagerService;
    private final RoleValidator roleValidator;

    @Operation(summary = "업체 관리자 생성", description = "업체 관리자를 생성합니다.")
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CompanyManagerResponse>> createCompany(
        @RequestHeader("X-User-Role") UserRole role,
        @RequestHeader("X-User-Id") Long currentUserId,
        @Valid @RequestBody CompanyManagerRequest companyManagerRequest) {
        roleValidator.validate(role, UserRole.MASTER_ADMIN);
        CompanyManagerResponse result = companyManagerService.createCompanyManager(companyManagerRequest);
        return ResponseEntity.ok(ApiResponse.success(result, "업체 관리자가 생성되었습니다"));
    }

    // FeignClient 용
    @Operation(summary = "업체 조회", description = "userId로 업체를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyUserId(
        @PathVariable(name = "userId") Long userId) {
        CompanyResponse result = companyManagerService.getCompanyUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(result, "업체가 조회되었습니다"));
    }
}

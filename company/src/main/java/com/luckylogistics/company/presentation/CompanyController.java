package com.luckylogistics.company.presentation;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.company.application.CompanyService;
import com.luckylogistics.company.application.dto.CompanyRequest;
import com.luckylogistics.company.application.dto.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
@Tag(name = "업체 API" , description = "업체 관련 API입니다.")
public class CompanyController {

    private final CompanyService companyService;
    private final RoleValidator roleValidator;

    @Operation(summary = "업체 목록 조회", description = "업체 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanies(
        @RequestParam(required = false) String name) {
        List<CompanyResponse> result = companyService.getCompanies(name);
        return ResponseEntity.ok(ApiResponse.success(result, "업체 목록이 조회되었습니다"));
    }

    @Operation(summary = "업체 조회", description = "업체를 조회합니다.")
    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(@PathVariable(name = "companyId") UUID companyId) {
        CompanyResponse result = companyService.getCompany(companyId);
        return ResponseEntity.ok(ApiResponse.success(result, "업체가 조회되었습니다"));
    }

    @Operation(summary = "업체 생성", description = "업체를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
        @RequestHeader("X-User-Role") UserRole role,
        @Valid @RequestBody CompanyRequest companyRequest) {
        roleValidator.validate(role, "MASTER_ADMIN", "HUB_MANAGER");
        CompanyResponse result = companyService.createCompany(companyRequest);
        return ResponseEntity.ok(ApiResponse.success(result, "업체가 생성되었습니다"));
    }

    @Operation(summary = "업체 수정", description = "업체를 수정합니다.")
    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> updateCompany(
        @RequestHeader("X-User-Role") UserRole role,
        @PathVariable(name = "companyId") UUID companyId,
        @Valid @RequestBody CompanyRequest companyRequest) {
        roleValidator.validate(role, "MASTER_ADMIN", "HUB_MANAGER", "COMPANY_MANAGER");
        companyService.updateCompany(companyId, companyRequest);
        return ResponseEntity.ok(ApiResponse.success(null, "업체가 수정되었습니다."));
    }

    @Operation(summary = "업체 삭제", description = "업체를 삭제합니다.")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(
        @RequestHeader("X-User-Role") UserRole role,
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable(name = "companyId") UUID companyId) {
        roleValidator.validate(role, UserRole.MASTER_ADMIN, UserRole.HUB_MANAGER);
        companyService.deleteCompany(userId, companyId);
        return ResponseEntity.ok(ApiResponse.success(null, "업체가 삭제되었습니다"));
    }

}

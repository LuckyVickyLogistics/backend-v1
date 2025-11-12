package com.luckylogistics.company.presentation;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.company.application.CompanyService;
import com.luckylogistics.company.application.dto.CompanyRequest;
import com.luckylogistics.company.application.dto.CompanyResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final RoleValidator roleValidator;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getCompanies(
        @RequestParam(required = false) String name) {
        List<CompanyResponse> result = companyService.getCompanies(name);
        return ResponseEntity.ok(ApiResponse.success(result, "업체 목록이 조회되었습니다"));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(@PathVariable(name = "companyId") UUID companyId) {
        CompanyResponse result = companyService.getCompany(companyId);
        return ResponseEntity.ok(ApiResponse.success(result, "업체가 조회되었습니다"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
        @RequestHeader("X-User-Role") String role,
        @Valid @RequestBody CompanyRequest companyRequest) {
        roleValidator.validate(role, "MASTER_ADMIN", "HUB_MANAGER");
        CompanyResponse result = companyService.createCompany(companyRequest);
        return ResponseEntity.ok(ApiResponse.success(result, "업체가 생성되었습니다"));
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> updateCompany(
        @RequestHeader("X-User-Role") String role,
        @PathVariable(name = "companyId") UUID companyId,
        @Valid @RequestBody CompanyRequest companyRequest) {
        roleValidator.validate(role, "MASTER_ADMIN", "HUB_MANAGER", "COMPANY_MANAGER");
        companyService.updateCompany(companyId, companyRequest);
        return ResponseEntity.ok(ApiResponse.success(null, "업체가 수정되었습니다."));
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(
        @RequestHeader("X-User-Role") String role,
        @RequestHeader("X-User-Id") String userId,
        @PathVariable(name = "companyId") UUID companyId) {
        roleValidator.validate(role, "MASTER_ADMIN", "HUB_MANAGER");
        companyService.deleteCompany(userId, companyId);
        return ResponseEntity.ok(ApiResponse.success(null, "업체가 삭제되었습니다"));
    }

}

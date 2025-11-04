package com.luckylogistics.company.presentation;

import com.luckylogistics.company.application.CompanyService;
import com.luckylogistics.company.application.dto.CompanyRequest;
import com.luckylogistics.company.application.dto.CompanyResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
public class CompanyController {
    //todo: 인가 처리

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getCompanies(
        @RequestParam(required = false) String name) {
        List<CompanyResponse> result = companyService.getCompanies(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable(name = "companyId") UUID companyId) {
        CompanyResponse result = companyService.getCompany(companyId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
        @RequestBody CompanyRequest companyRequest) {
        CompanyResponse result = companyService.createCompany(companyRequest);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<Void> updateCompany(
        @PathVariable(name = "companyId") UUID companyId,
        @RequestBody CompanyRequest companyRequest) {
        companyService.updateCompany(companyId, companyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
        @RequestHeader Long userId, //todo: 더 알아보기
        @PathVariable(name = "companyId") UUID companyId) {
        companyService.deleteCompany(userId, companyId);
        return ResponseEntity.ok().build();
    }

}

package com.example.iksystem.controller;

import com.example.iksystem.service.CompanyService;
import com.example.iksystem.dto.company.CompanyCreateDto;
import com.example.iksystem.dto.company.CompanyResponseDto;
import com.example.iksystem.dto.company.CompanyUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * Bu sınıf Company Controller nesnesini temsil eder.
 */


@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    /// Bu metod, yeni bir şirket oluşturmak için HTTP POST isteğini işler.
    @PostMapping("/api/admin/companies")
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody @Valid CompanyCreateDto dto) {
        CompanyResponseDto response = companyService.createCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /// Bu metod, bir şirketin detaylarını getirmek için HTTP GET isteğini işler.
    @GetMapping("api/companies/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    /// Bu metod, bir şirketin bilgilerini güncellemek için HTTP PUT isteğini işler.
    @PutMapping("/api/admin/companies/{id}")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable UUID id, @RequestBody @Valid CompanyUpdateDto dto) {
        return ResponseEntity.ok(companyService.updateCompany(id, dto));
    }

    /// Bu metod, tüm şirketleri getirmek için HTTP GET isteğini işler.
    @GetMapping("api/companies")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> response = companyService.getAllCompanies();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/companies/active")
    public ResponseEntity<List<CompanyResponseDto>> getActiveCompaniesForAdmin() {
        List<CompanyResponseDto> response = companyService.getActiveCompaniesForAdmin();
        return ResponseEntity.ok(response);
    }
}

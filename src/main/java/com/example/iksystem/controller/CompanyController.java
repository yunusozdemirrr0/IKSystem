package com.example.iksystem.controller;

import com.example.iksystem.CompanyService;
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


@RestController
@RequestMapping("/api/admin/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody @Valid CompanyCreateDto dto) {
        CompanyResponseDto response = companyService.createCompany(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> updateCompany(@PathVariable  UUID id, @RequestBody @Valid CompanyUpdateDto dto) {
        return ResponseEntity.ok(companyService.updateCompany(id,dto));
    }
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> response = companyService.getAllCompanies();
        return ResponseEntity.ok(response);
    }

}

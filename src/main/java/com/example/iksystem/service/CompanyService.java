package com.example.iksystem.service;

import com.example.iksystem.dto.company.CompanyCreateDto;
import com.example.iksystem.dto.company.CompanyResponseDto;
import com.example.iksystem.dto.company.CompanyUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface CompanyService {
    CompanyResponseDto createCompany(CompanyCreateDto dto);

    CompanyResponseDto updateCompany( UUID id, CompanyUpdateDto dto);

    CompanyResponseDto getCompanyById(UUID id);

    List<CompanyResponseDto> getAllCompanies();
}

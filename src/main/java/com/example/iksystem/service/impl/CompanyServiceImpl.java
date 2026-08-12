package com.example.iksystem.service.impl;

import com.example.iksystem.exception.AlreadyExistsException;
import com.example.iksystem.repository.CompanyRepository;
import com.example.iksystem.service.CompanyService;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.dto.company.CompanyCreateDto;
import com.example.iksystem.dto.company.CompanyResponseDto;
import com.example.iksystem.dto.company.CompanyUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.iksystem.entity.CompanyEntity;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public CompanyResponseDto createCompany(CompanyCreateDto dto){
        if (companyRepository.existsByCompanyNameIgnoreCase(dto.getCompanyName())) {
            throw new AlreadyExistsException("Company already exists!");
        }
        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyName(dto.getCompanyName())
                .isActive(true)
                .email(dto.getCompanyEmail())
                .address(dto.getCompanyAddress())
                .telephone(dto.getCompanyPhoneNumber())
                .logoUrl(dto.getCompanyLogoUrl())
                .latitude(dto.getCompanyLatitude())
                .longitude(dto.getCompanyLongitude())

                .build();
        CompanyEntity savedCompany = companyRepository.save(companyEntity);
        return CompanyResponseDto.builder()
                .id(savedCompany.getId())
                .companyName(savedCompany.getCompanyName())

                .companyEmail(savedCompany.getEmail())
                .companyAddress(savedCompany.getAddress())
                .companyPhone(savedCompany.getTelephone())
                .companyLogoUrl(savedCompany.getLogoUrl())
                .companyLatitude(savedCompany.getLatitude())
                .companyLongitude(savedCompany.getLongitude())
                .build();
    }
    @Override
    public CompanyResponseDto getCompanyById(UUID id) {
        CompanyEntity company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found!"));
        return CompanyResponseDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .companyEmail(company.getEmail())
                .companyAddress(company.getAddress())
                .companyPhone(company.getTelephone())
                .companyLogoUrl(company.getLogoUrl())
                .companyLatitude(company.getLatitude())
                .companyLongitude(company.getLongitude())
                .build();
    }

    @Override
    @Transactional

    public CompanyResponseDto updateCompany(UUID id,CompanyUpdateDto dto) {
        CompanyEntity company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found!"));
        if (companyRepository.existsByCompanyNameIgnoreCaseAndIdNot(dto.getCompanyName(), id)) {
            throw new AlreadyExistsException("Company already exists!");
        }
        company.setCompanyName(dto.getCompanyName());
        CompanyEntity updatedCompany = companyRepository.save(company);
        return CompanyResponseDto.builder()
                .id(updatedCompany.getId())
                .companyName(updatedCompany.getCompanyName())
                .companyEmail(updatedCompany.getEmail())
                .companyAddress(updatedCompany.getAddress())
                .companyPhone(updatedCompany.getTelephone())
                .companyLogoUrl(updatedCompany.getLogoUrl())
                .companyLatitude(updatedCompany.getLatitude())
                .companyLongitude(updatedCompany.getLongitude())
                .build();

    }
    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        List<CompanyEntity> companies = companyRepository.findAll();
        return companies.stream().map(company -> CompanyResponseDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .companyEmail(company.getEmail())
                .companyAddress(company.getAddress())
                .companyPhone(company.getTelephone())
                .companyLogoUrl(company.getLogoUrl())
                .companyLatitude(company.getLatitude())
                .companyLongitude(company.getLongitude())
                .build()).toList();
    }




}

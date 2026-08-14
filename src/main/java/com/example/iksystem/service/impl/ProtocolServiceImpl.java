package com.example.iksystem.service.impl;

import com.example.iksystem.dto.protocol.ProtocolAttachmentResponseDto;
import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;
import com.example.iksystem.dto.user.UserProtocolDetailDto;
import com.example.iksystem.dto.user.UserProtocolListDto;
import com.example.iksystem.entity.CategoriesEntity;
import com.example.iksystem.entity.CompanyEntity;
import com.example.iksystem.entity.ProtocolAttachmentEntity;
import com.example.iksystem.entity.ProtocolsEntity;
import com.example.iksystem.enums.model.constant.ProtocolStatus;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.repository.CategoryRepository;
import com.example.iksystem.repository.CompanyRepository;
import com.example.iksystem.repository.ProtocolRepository;
import com.example.iksystem.service.ProtocolService;
import com.example.iksystem.specification.ProtocolSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Bu sınıf Protocol Service Impl nesnesini temsil eder.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProtocolResponseDto createProtocol(ProtocolCreateDto dto, List<MultipartFile> files) {
        validateDates(dto.getBeginDate(), dto.getEndDate());

        CompanyEntity company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + dto.getCompanyId()));

        CategoriesEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        ProtocolsEntity protocolsEntity = ProtocolsEntity.builder()
                .title(dto.getTitle())
                .discountPercentage(dto.getDiscountPercentage())
                .discountDetailsText(dto.getDiscountDetailText())
                .specialConditions(dto.getSpecialConditions())
                .beginDate(dto.getBeginDate())
                .endDate(dto.getEndDate())
                .protocolStatus(dto.getProtocolStatus() != null ? dto.getProtocolStatus() : ProtocolStatus.ACTIVE)
                .company(company)
                .category(category)
                .protocolFiles(new ArrayList<>())
                .build();

        if (dto.getAttachments() != null && !dto.getAttachments().isEmpty()) {
            List<ProtocolAttachmentEntity> attachmentEntities = dto.getAttachments().stream()
                    .map(attDto -> ProtocolAttachmentEntity.builder()
                            .filePathUrl(attDto.getFilePathUrl())
                            .fileName(attDto.getFileName() != null ? attDto.getFileName() : "ek-dosya")
                            .attachmentType(attDto.getAttachmentType())
                            .showPersonel(attDto.getShowPersonel() != null ? attDto.getShowPersonel() : false)
                            .protocol(protocolsEntity)
                            .build())
                    .toList();

            protocolsEntity.getProtocolFiles().addAll(attachmentEntities);
        }

        ProtocolsEntity savedEntity = protocolRepository.save(protocolsEntity);
        return mapToResponseDto(savedEntity);
    }

    @Override
    @Transactional
    public ProtocolResponseDto updateProtocol(UUID id, ProtocolUpdateDto dto, List<MultipartFile> files) {
        ProtocolsEntity existingProtocol = protocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocol not found with id: " + id));

        LocalDate newBeginDate = dto.getBeginDate() != null ? dto.getBeginDate() : existingProtocol.getBeginDate();
        LocalDate newEndDate = dto.getEndDate() != null ? dto.getEndDate() : existingProtocol.getEndDate();

        validateDates(newBeginDate, newEndDate);

        if (dto.getCompanyId() != null) {
            CompanyEntity company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + dto.getCompanyId()));
            existingProtocol.setCompany(company);
        }

        if (dto.getCategoryId() != null) {
            CategoriesEntity category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            existingProtocol.setCategory(category);
        }

        existingProtocol.setTitle(dto.getTitle());
        existingProtocol.setDiscountPercentage(dto.getDiscountPercentage());
        existingProtocol.setDiscountDetailsText(dto.getDiscountDetailText());
        existingProtocol.setSpecialConditions(dto.getSpecialConditions());
        existingProtocol.setBeginDate(newBeginDate);
        existingProtocol.setEndDate(newEndDate);

        if (dto.getProtocolStatus() != null) {
            existingProtocol.setProtocolStatus(dto.getProtocolStatus());
        }

        if (dto.getAttachments() != null) {
            existingProtocol.getProtocolFiles().clear();
            List<ProtocolAttachmentEntity> updatedAttachments = dto.getAttachments().stream()
                    .map(attDto -> ProtocolAttachmentEntity.builder()
                            .filePathUrl(attDto.getFilePathUrl())
                            .fileName(attDto.getFileName() != null ? attDto.getFileName() : "ek-dosya")
                            .attachmentType(attDto.getAttachmentType())
                            .showPersonel(attDto.getShowPersonel() != null ? attDto.getShowPersonel() : false)
                            .protocol(existingProtocol)
                            .build())
                    .toList();

            existingProtocol.getProtocolFiles().addAll(updatedAttachments);
        }

        ProtocolsEntity updatedEntity = protocolRepository.save(existingProtocol);
        return mapToResponseDto(updatedEntity);
    }

    private void validateDates(LocalDate beginDate, LocalDate endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("Begin date and end date are required");
        }
        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Begin date cannot be after end date");
        }
    }

    @Override
    @Transactional
    public void toggleProtocolStatus(UUID id) {
        ProtocolsEntity existingProtocol = protocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocol not found with id: " + id));

        existingProtocol.setProtocolStatus(
                existingProtocol.getProtocolStatus() == ProtocolStatus.ACTIVE ? ProtocolStatus.PASSIVE : ProtocolStatus.ACTIVE
        );
        protocolRepository.save(existingProtocol);
    }

    @Override
    public Page<ProtocolResponseDto> getAllProtocolsForAdmin(Pageable pageable) {
        return protocolRepository.findAll(pageable)
                .map(this::mapToResponseDto);
    }

    @Override
    public Page<UserProtocolListDto> getActiveProtocolsForUser(String keyword, UUID categoryId, Pageable pageable) {
        Specification<ProtocolsEntity> spec = Specification.where(ProtocolSpecification.isActiveAndNotExpired())
                .and(ProtocolSpecification.containsKeyword(keyword))
                .and(ProtocolSpecification.hasCategoryId(categoryId));

        return protocolRepository.findAll(spec, pageable)
                .map(this::convertToUserProtocolListDto);
    }

    @Override
    public UserProtocolDetailDto getProtocolDetailForUser(UUID id) {
        ProtocolsEntity protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocol not found with id: " + id));

        if (protocol.getProtocolStatus() != ProtocolStatus.ACTIVE || protocol.getEndDate().isBefore(LocalDate.now())) {
            throw new ResourceNotFoundException("Protocol is not active or has expired");
        }

        return convertToUserProtocolDetailDto(protocol);
    }

    private ProtocolResponseDto mapToResponseDto(ProtocolsEntity entity) {
        List<ProtocolAttachmentResponseDto> attachmentDtos = Collections.emptyList();

        if (entity.getProtocolFiles() != null && !entity.getProtocolFiles().isEmpty()) {
            attachmentDtos = entity.getProtocolFiles().stream()
                    .map(att -> ProtocolAttachmentResponseDto.builder()
                            .id(att.getId())
                            .fileUrl(att.getFilePathUrl())
                            .fileName(att.getFileName())
                            .attachmentType(att.getAttachmentType())
                            .showPersonel(att.getShowPersonel())
                            .build())
                    .toList();
        }

        return ProtocolResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .discountPercentage(entity.getDiscountPercentage())
                .discountDetailText(entity.getDiscountDetailsText())
                .specialConditions(entity.getSpecialConditions())
                .beginDate(entity.getBeginDate())
                .endDate(entity.getEndDate())
                .protocolStatus(entity.getProtocolStatus())
                .companyId(entity.getCompany() != null ? entity.getCompany().getId() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .attachments(attachmentDtos)
                .build();
    }

    private UserProtocolListDto convertToUserProtocolListDto(ProtocolsEntity protocol) {
        return UserProtocolListDto.builder()
                .id(protocol.getId())
                .title(protocol.getTitle())
                .companyName(protocol.getCompany() != null ? protocol.getCompany().getCompanyName() : null)
                .categoryName(protocol.getCategory() != null ? protocol.getCategory().getCategoryName() : null)
                .discountPercentage(protocol.getDiscountPercentage())
                .logoUrl(protocol.getCompany() != null ? protocol.getCompany().getLogoUrl() : null)
                .endDate(protocol.getEndDate())
                .build();
    }

    private UserProtocolDetailDto convertToUserProtocolDetailDto(ProtocolsEntity protocol) {
        List<String> fileDownloadUrls = Collections.emptyList();
        if (protocol.getProtocolFiles() != null && !protocol.getProtocolFiles().isEmpty()) {
            fileDownloadUrls = protocol.getProtocolFiles().stream()
                    .filter(att -> Boolean.TRUE.equals(att.getShowPersonel()))
                    .map(file -> ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/v1/admin/files/download/")
                            .path(file.getId().toString())
                            .toUriString())
                    .toList();
        }

        String mapUrl = null;
        if (protocol.getCompany() != null && protocol.getCompany().getLatitude() != null && protocol.getCompany().getLongitude() != null) {
            mapUrl = protocol.getCompany().getLatitude() + "," + protocol.getCompany().getLongitude();
        }

        return UserProtocolDetailDto.builder()
                .id(protocol.getId())
                .title(protocol.getTitle())
                .companyName(protocol.getCompany() != null ? protocol.getCompany().getCompanyName() : null)
                .categoryName(protocol.getCategory() != null ? protocol.getCategory().getCategoryName() : null)
                .logoUrl(protocol.getCompany() != null ? protocol.getCompany().getLogoUrl() : null)
                .discountDetailsText(protocol.getDiscountDetailsText())
                .specialConditions(protocol.getSpecialConditions())
                .telephone(protocol.getCompany() != null ? protocol.getCompany().getTelephone() : null)
                .email(protocol.getCompany() != null ? protocol.getCompany().getEmail() : null)
                .address(protocol.getCompany() != null ? protocol.getCompany().getAddress() : null)
                .mapUrl(mapUrl)
                .beginDate(protocol.getBeginDate())
                .endDate(protocol.getEndDate())
                .fileDownloadUrls(fileDownloadUrls)
                .build();
    }
}
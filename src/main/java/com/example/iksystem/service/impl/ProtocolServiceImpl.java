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
import com.example.iksystem.enums.model.constant.AttachmentType;
import com.example.iksystem.enums.model.constant.ProtocolStatus;
import com.example.iksystem.exception.ResourceNotFoundException;
import com.example.iksystem.repository.CategoryRepository;
import com.example.iksystem.repository.CompanyRepository;
import com.example.iksystem.repository.ProtocolRepository;
import com.example.iksystem.service.FileStorageService;
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
    private final FileStorageService fileStorageService;

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

        // 1. JSON içinden gelen ekleri ekle
        if (dto.getAttachments() != null && !dto.getAttachments().isEmpty()) {
            List<ProtocolAttachmentEntity> attachmentEntities = dto.getAttachments().stream()
                    .map(attDto -> ProtocolAttachmentEntity.builder()
                            .filePathUrl(attDto.getFilePathUrl())
                            .fileName(attDto.getFileName() != null ? attDto.getFileName() : "ek-dosya")
                            .attachmentType(attDto.getAttachmentType())
                            .showPersonel(attDto.getShowPersonel() != null ? attDto.getShowPersonel() : true)
                            .protocol(protocolsEntity)
                            .build())
                    .toList();

            protocolsEntity.getProtocolFiles().addAll(attachmentEntities);
        }

        // 2. Multipart ile yüklenen fiziksel dosyaları diske kaydet
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String storedFileName = fileStorageService.storeFile(file);
                    String fileDownloadUri = buildPublicFileUrl(storedFileName);

                    ProtocolAttachmentEntity physicalAttachment = ProtocolAttachmentEntity.builder()
                            .fileName(file.getOriginalFilename())
                            .filePathUrl(fileDownloadUri)
                            .attachmentType(AttachmentType.CAMPAIGN_POSTER)
                            .showPersonel(true)
                            .protocol(protocolsEntity)
                            .build();

                    protocolsEntity.getProtocolFiles().add(physicalAttachment);
                }
            }
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

        // DTO'dan yeni ekler geldiyse listeyi yenile
        if (dto.getAttachments() != null) {
            existingProtocol.getProtocolFiles().clear();
            List<ProtocolAttachmentEntity> updatedAttachments = dto.getAttachments().stream()
                    .map(attDto -> ProtocolAttachmentEntity.builder()
                            .filePathUrl(attDto.getFilePathUrl())
                            .fileName(attDto.getFileName() != null ? attDto.getFileName() : "ek-dosya")
                            .attachmentType(attDto.getAttachmentType())
                            .showPersonel(attDto.getShowPersonel() != null ? attDto.getShowPersonel() : true)
                            .protocol(existingProtocol)
                            .build())
                    .toList();

            existingProtocol.getProtocolFiles().addAll(updatedAttachments);
        }

        // Yeni yüklenen fiziksel dosyaları ekle
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String storedFileName = fileStorageService.storeFile(file);
                    String fileDownloadUri = buildPublicFileUrl(storedFileName);

                    ProtocolAttachmentEntity physicalAttachment = ProtocolAttachmentEntity.builder()
                            .fileName(file.getOriginalFilename())
                            .filePathUrl(fileDownloadUri)
                            .attachmentType(AttachmentType.CAMPAIGN_POSTER)
                            .showPersonel(true)
                            .protocol(existingProtocol)
                            .build();

                    existingProtocol.getProtocolFiles().add(physicalAttachment);
                }
            }
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
                .companyName(entity.getCompany() != null ? entity.getCompany().getCompanyName() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getCategoryName() : null)
                .attachments(attachmentDtos)
                .build();
    }

    private UserProtocolListDto convertToUserProtocolListDto(ProtocolsEntity protocol) {
        AttachmentType primaryAttachmentType = null;
        String primaryFileUrl = null;

        if (protocol.getProtocolFiles() != null && !protocol.getProtocolFiles().isEmpty()) {
            // Personele açık ve sözleşme olmayan ilk afiş/broşürü bul
            ProtocolAttachmentEntity primaryAttachment = protocol.getProtocolFiles().stream()
                    .filter(att -> att.getShowPersonel() == null || Boolean.TRUE.equals(att.getShowPersonel()))
                    .filter(att -> att.getAttachmentType() == AttachmentType.CAMPAIGN_POSTER || att.getAttachmentType() == AttachmentType.BROCHURE)
                    .findFirst()
                    .orElse(null);

            if (primaryAttachment != null) {
                primaryAttachmentType = primaryAttachment.getAttachmentType();
                primaryFileUrl = buildPublicFileUrl(primaryAttachment.getFilePathUrl()); // Tam link üretilir
            }
        }

        return UserProtocolListDto.builder()
                .id(protocol.getId())
                .title(protocol.getTitle())
                .companyName(protocol.getCompany() != null ? protocol.getCompany().getCompanyName() : null)
                .categoryName(protocol.getCategory() != null ? protocol.getCategory().getCategoryName() : null)
                .discountPercentage(protocol.getDiscountPercentage())
                .logoUrl(protocol.getCompany() != null ? protocol.getCompany().getLogoUrl() : null)
                .beginDate(protocol.getBeginDate())
                .endDate(protocol.getEndDate())
                .discountDetailsText(protocol.getDiscountDetailsText())
                .specialConditions(protocol.getSpecialConditions())
                .protocolStatus(protocol.getProtocolStatus() == ProtocolStatus.ACTIVE)
                .fileUrl(primaryFileUrl)
                .attachmentType(primaryAttachmentType)
                .build();
    }

    private UserProtocolDetailDto convertToUserProtocolDetailDto(ProtocolsEntity protocol) {
        List<String> fileDownloadUrls = Collections.emptyList();
        List<ProtocolAttachmentResponseDto> userAttachments = Collections.emptyList();

        if (protocol.getProtocolFiles() != null && !protocol.getProtocolFiles().isEmpty()) {
            // Sadece personele açık olan ve SPECIAL_CONTRACTS OLMAYAN dosyaları al
            List<ProtocolAttachmentEntity> allowedFiles = protocol.getProtocolFiles().stream()
                    .filter(att -> att.getShowPersonel() == null || Boolean.TRUE.equals(att.getShowPersonel()))
                    .filter(att -> att.getAttachmentType() == AttachmentType.CAMPAIGN_POSTER
                            || att.getAttachmentType() == AttachmentType.BROCHURE)
                    .toList();

            // Dosya indirme / görüntüleme URL'leri
            fileDownloadUrls = allowedFiles.stream()
                    .map(att -> buildPublicFileUrl(att.getFilePathUrl()))
                    .toList();

            // Dosya detay listesi
            userAttachments = allowedFiles.stream()
                    .map(att -> ProtocolAttachmentResponseDto.builder()
                            .id(att.getId())
                            .fileUrl(buildPublicFileUrl(att.getFilePathUrl()))
                            .fileName(att.getFileName())
                            .attachmentType(att.getAttachmentType())
                            .showPersonel(att.getShowPersonel())
                            .build())
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
                .attachments(userAttachments)
                .build();
    }

    /**
     * Verilen dosya yolu ya da adı eğer ham link değilse,
     * sisteme ait `/api/v1/files/download/...` tam URL'sine dönüştürür.
     */
    private String buildPublicFileUrl(String rawPath) {
        if (rawPath == null || rawPath.isBlank()) {
            return null;
        }

        // Zaten tam URL ise (harici kaynak) olduğu gibi dön
        if (rawPath.startsWith("http://") || rawPath.startsWith("https://")) {
            return rawPath;
        }

        // Eğer veritabanında eski admin linki olarak kalmışsa, doğru olanı ayıkla
        if (rawPath.contains("/download/")) {
            String fileName = rawPath.substring(rawPath.lastIndexOf("/download/") + 10);
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/files/download/")
                    .path(fileName)
                    .toUriString();
        }

        // Sadece dosya adı olarak tutulmuşsa
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/download/")
                .path(rawPath)
                .toUriString();
    }
}
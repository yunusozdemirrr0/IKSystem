package com.example.iksystem.service;
import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;
import com.example.iksystem.dto.user.UserProtocolDetailDto;
import com.example.iksystem.dto.user.UserProtocolListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
/**
 * Bu arayüz Protocol Service davranışlarını tanımlar.
 */

public interface ProtocolService {
    ProtocolResponseDto createProtocol(ProtocolCreateDto protocolCreateDto, List<MultipartFile> files);
    ProtocolResponseDto updateProtocol(UUID id, ProtocolUpdateDto protocolUpdateDto, List<MultipartFile> files);
    void toggleProtocolStatus(UUID id);
    Page<ProtocolResponseDto> getAllProtocolsForAdmin(Pageable pageable);
    Page<UserProtocolListDto> getActiveProtocolsForUser(String keyword,UUID categoryId,Pageable pageable);
    UserProtocolDetailDto getProtocolDetailForUser(UUID id);

}

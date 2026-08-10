package com.example.iksystem;
import com.example.iksystem.dto.protocol.ProtocolCreateDto;
import com.example.iksystem.dto.protocol.ProtocolResponseDto;
import com.example.iksystem.dto.protocol.ProtocolUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProtocolService {
    ProtocolResponseDto createProtocol(ProtocolCreateDto protocolCreateDto, List<MultipartFile> files);
    ProtocolResponseDto updateProtocol(UUID id, ProtocolUpdateDto protocolUpdateDto, List<MultipartFile> files);
    void toggleProtocolStatus(UUID id);
    Page<ProtocolResponseDto> getAllProtocolsForAdmin(Pageable pageable);


}

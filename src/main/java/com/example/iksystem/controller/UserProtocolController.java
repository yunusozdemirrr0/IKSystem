    package com.example.iksystem.controller;

    import com.example.iksystem.dto.user.UserProtocolDetailDto;
    import com.example.iksystem.dto.user.UserProtocolListDto;
    import com.example.iksystem.service.ProtocolService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.data.web.PageableDefault;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.UUID;

    /**
     * Bu sınıf User Protocol Controller nesnesini temsil eder.
     */
    @RestController
    @RequestMapping("/api/v1/protocols")
    @RequiredArgsConstructor
    @Tag(name = "User Protocol Controller", description = "Controller for user protocol operations")
    public class UserProtocolController {

        private final ProtocolService protocolService;

        @Operation(
                summary = "Aktif protokolleri sayfalı listeler",
                description = "Kullanıcılar için süresi geçmemiş ve aktif olan protokolleri arama ve kategori filtreleriyle sayfalı olarak getirir."
        )
        @GetMapping
        public ResponseEntity<Page<UserProtocolListDto>> getActiveProtocolsForUser(
                @RequestParam(required = false) String search,
                @RequestParam(required = false) UUID categoryId,
                @PageableDefault(size = 10, sort = "beginDate", direction = Sort.Direction.DESC) Pageable pageable) {

            Page<UserProtocolListDto> protocols = protocolService.getActiveProtocolsForUser(search, categoryId, pageable);
            return ResponseEntity.ok(protocols);
        }

        @Operation(
                summary = "Protokol detayını getirir",
                description = "ID'si verilen aktif protokolün detaylarını, iletişim ve harita bilgilerini ve izin verilen ek dosyalarını (Broşür/Afiş) getirir."
        )
        @GetMapping("/{id}")
        public ResponseEntity<UserProtocolDetailDto> getProtocolDetailForUser(@PathVariable UUID id) {
            UserProtocolDetailDto protocol = protocolService.getProtocolDetailForUser(id);
            return ResponseEntity.ok(protocol);
        }
    }
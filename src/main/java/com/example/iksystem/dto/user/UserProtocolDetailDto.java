    package com.example.iksystem.dto.user;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;
    import java.util.List;
    import java.util.UUID;
/**
 * Bu sınıf User Protocol Detail Dto nesnesini temsil eder.
 */

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserProtocolDetailDto {
        private UUID id;
        private String title;
        private String companyName;
        private String categoryName;
        private String logoUrl;
        private String discountDetailsText;
        private String specialConditions;
        private String telephone;
        private String email;
        private String address;;
        private String mapUrl;
        private LocalDate beginDate;
        private LocalDate endDate;
        private List<String> fileDownloadUrls;
    }

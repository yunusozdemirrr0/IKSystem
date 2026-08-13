package com.example.iksystem.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bu sınıf Şirket Güncelleme (Update) DTO nesnesini temsil eder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateDto {

    @NotBlank(message = "Şirket adı boş bırakılamaz")
    private String companyName;

    @NotBlank(message = "Adres alanı boş bırakılamaz")
    private String address;

    @NotBlank(message = "E-posta alanı boş bırakılamaz")
    @Email(message = "Geçersiz e-posta formatı")
    private String email;

    // cURL / Postman üzerinden 'phoneNumber' veya 'telephone' gelse de service seviyesindeki 'telephone' ismiyle eşleşir
    @JsonProperty("phoneNumber")
    @Pattern(regexp = "^(\\+90|0)?[1-9][0-9]{9}$", message = "Geçersiz telefon numarası formatı")
    private String telephone;

    @Size(max = 255, message = "Logo URL en fazla 255 karakter olabilir")
    private String logoUrl;

    @NotNull(message = "Enlem (latitude) alanı boş bırakılamaz")
    @DecimalMin(value = "-90.0", message = "Enlem -90.0 ile 90.0 arasında olmalıdır")
    @DecimalMax(value = "90.0", message = "Enlem -90.0 ile 90.0 arasında olmalıdır")
    private Double latitude;

    @NotNull(message = "Boylam (longitude) alanı boş bırakılamaz")
    @DecimalMin(value = "-180.0", message = "Boylam -180.0 ile 180.0 arasında olmalıdır")
    @DecimalMax(value = "180.0", message = "Boylam -180.0 ile 180.0 arasında olmalıdır")
    private Double longitude;

    @NotNull(message = "isActive alanı boş bırakılamaz")
    private Boolean isActive;
}
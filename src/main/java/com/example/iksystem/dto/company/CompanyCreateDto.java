package com.example.iksystem.dto.company;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateDto {
    @NotBlank(message = "companyName cannot be blank")
    @Column(name = "company_name")
    private String companyName;
    @NotBlank(message = "address cannot be blank")
    @Column(name = "address")
    private String companyAddress;
    @NotBlank(message = "invalid email format")
    @Email(message = "invalid email format")
    @Column(name = "e-posta")
    private String companyEmail;
    @Pattern(regexp = "^(\\+90|0)?[1-9][0-9]{9}$", message = "invalid phone number format")
    @Column(name = "telephone")
    private String companyPhoneNumber;
    @Size(max = 255)
    @Column(name = "logo_url")
    private String companyLogoUrl;
    @NotNull(message = "latitude cannot be null")
    @DecimalMin(value = "-90.0", message = "latitude must be greater than or equal to -90.0")
    @DecimalMax(value = "90.0", message = "latitude must be less than or equal to 90.0")
    @Column(name = "latitude")
    private Double companyLatitude;
    @NotNull(message = "longitude cannot be null")
    @DecimalMin(value = "-180.0", message = "longitude must be greater than or equal to -180.0")
    @DecimalMax(value = "180.0", message = "longitude must be less than or equal to 180.0")
    @Column(name = "longitude")
    private Double companyLongitude;




}

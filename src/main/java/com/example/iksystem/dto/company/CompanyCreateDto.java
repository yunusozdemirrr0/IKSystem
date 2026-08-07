package com.example.iksystem.dto.company;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateDto {
    @NotBlank(message = "companyName cannot be blank")
    private String companyName;
    @NotBlank(message = "address cannot be blank")
    private String address;
    @NotBlank(message = "invalid email format")
    @Email(message = "invalid email format")
    private String email;
    @Pattern(regexp = "^(\\+90|0)?[1-9][0-9]{9}$", message = "invalid phone number format")
    private String phoneNumber;
    @Size(max = 255)
    private String logoUrl;
    @NotNull(message = "latitude cannot be null")
    @DecimalMin(value = "-90.0", message = "latitude must be greater than or equal to -90.0")
    @DecimalMax(value = "90.0", message = "latitude must be less than or equal to 90.0")
    private Double latitude;
    @NotNull(message = "longitude cannot be null")
    @DecimalMin(value = "-180.0", message = "longitude must be greater than or equal to -180.0")
    @DecimalMax(value = "180.0", message = "longitude must be less than or equal to 180.0")
    private Double longitude;




}

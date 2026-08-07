package com.example.iksystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Entity
@Table(name = "companies")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @NotBlank(message = "companyName cannot be blank")
    private String companyName;

    private String address;
    @Pattern(regexp = "^(\\+90|0)?[1-9][0-9]{9}$", message = "invalid phone number format")
    private String telephone;
    @NotBlank(message = "email cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format")
    private String email;
    private boolean status;
    @URL(message = "invalid URL format")
    private String logoUrl;

    @NotNull(message = "latitude cannot be null") @Size(min = -90, max = 90)
    private Double latitude;
    @NotNull(message = "longitude cannot be null") @Size(min = -180, max = 180)
    private Double longitude;
    @NotNull
    private boolean isActive;

}

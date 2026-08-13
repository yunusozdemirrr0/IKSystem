package com.example.iksystem.entity;

import com.example.iksystem.enums.model.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Bu sınıf Users Entity nesnesini temsil eder.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name_surname", nullable = false)
    @NotNull(message = "Name and surname cannot be null")
    private String nameSurname;

    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Email cannot be null")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotNull(message = "Role cannot be null")
    private Role role;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive = true;
}
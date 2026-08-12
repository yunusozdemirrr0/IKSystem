package com.example.iksystem.entity;
import com.example.iksystem.enums.model.constant.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name_surname", nullable = false)
    private String nameSurname;
    @Column(unique = true, nullable = false, name = "e-posta")
    private String email;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "status",nullable = false)
    private boolean isActive;





}

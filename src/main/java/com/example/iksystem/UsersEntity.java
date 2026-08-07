package com.example.iksystem;
import com.example.iksystem.enums.model.constant.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name_surname;
    @Column(unique = true, nullable = false,name = "e-posta")
    private String email;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "status",nullable = false)
    private boolean isActive;





}

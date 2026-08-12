package com.example.iksystem.repository;

import com.example.iksystem.entity.UsersEntity;
import com.example.iksystem.enums.model.constant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);

    Optional<UsersEntity> findByEmailIgnoreCase(String email);

    List<UsersEntity> findAllByRole(Role role);
    List<UsersEntity> findAllByIsActive(Boolean isActive);

}

package com.example.iksystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProtocolRepository extends JpaRepository<ProtocolsEntity, UUID>, JpaSpecificationExecutor<ProtocolsEntity> {

    boolean existsByCompanyId(UUID companyId);
    boolean existsByCategoryId(UUID categoryId);


    Page<ProtocolsEntity> findAllByCategoryId(UUID categoryId, Pageable pageable);
    Page<ProtocolsEntity> findAllByCompanyId(UUID companyId, Pageable pageable);

}

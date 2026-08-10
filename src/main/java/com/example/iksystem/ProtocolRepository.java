package com.example.iksystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProtocolRepository extends JpaRepository<ProtocolsEntity, UUID>, JpaSpecificationExecutor<ProtocolsEntity> {

    boolean existsByCompanyId(UUID companyId);
    boolean existsByCategoryId(UUID categoryId);


    Page<ProtocolsEntity> findAllByCategoryId(UUID categoryId, Pageable pageable);
    Page<ProtocolsEntity> findAllByCompanyId(UUID companyId, Pageable pageable);
    List<ProtocolsEntity> findAllByEndDateBeforeAndProtocolStatusTrue(LocalDate date);
    List<ProtocolsEntity> findAllByEndDateAndProtocolStatusTrue(LocalDate endDate);

}

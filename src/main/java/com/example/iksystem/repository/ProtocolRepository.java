package com.example.iksystem.repository;

import com.example.iksystem.entity.ProtocolsEntity;
import com.example.iksystem.enums.model.constant.ProtocolStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * Bu arayüz Protocol Repository davranışlarını tanımlar.
 */

//Bu arayüz, ProtocolsEntity ile ilgili veri erişim işlemlerini tanımlar ve JpaRepository ile JpaSpecificationExecutor arayüzlerini genişletir.
public interface ProtocolRepository extends JpaRepository<ProtocolsEntity, UUID>, JpaSpecificationExecutor<ProtocolsEntity> {

    boolean existsByCompanyId(UUID companyId);
    boolean existsByCategoryId(UUID categoryId);


    Page<ProtocolsEntity> findAllByCategoryId(UUID categoryId, Pageable pageable);
    Page<ProtocolsEntity> findAllByCompanyId(UUID companyId, Pageable pageable);

    // Bu yöntem, belirli bir bitiş tarihinden önceki ve aktif olan protokolleri bulur.
    List<ProtocolsEntity> findAllByEndDateBeforeAndProtocolStatus(LocalDate date, ProtocolStatus protocolStatus);

    // Bu yöntem, belirli bir bitiş tarihine sahip ve aktif olan protokolleri bulur.
    List<ProtocolsEntity> findAllByEndDateAndProtocolStatus(LocalDate endDate, ProtocolStatus protocolStatus);

}

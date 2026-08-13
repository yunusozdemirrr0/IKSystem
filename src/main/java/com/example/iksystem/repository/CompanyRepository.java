package com.example.iksystem.repository;

import com.example.iksystem.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
/**
 * Bu arayüz Company Repository davranışlarını tanımlar.
 */
//Bu arayüz, CompanyEntity ile ilgili veritabanı işlemlerini gerçekleştirmek için JpaRepository'yi genişletir. UUID türünde birincil anahtar kullanır.
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    // Şirket adının var olup olmadığını kontrol eden bir yöntem tanımlar. Büyük/küçük harf duyarsızdır.
    boolean existsByCompanyNameIgnoreCase(String companyName);

    // Bu yöntem, şirket adının büyük/küçük harf duyarsız olarak var olup olmadığını kontrol eder. Eğer şirket adı mevcutsa true, aksi takdirde false döner.
    boolean existsByCompanyNameIgnoreCaseAndIdNot(String companyName, UUID id);

}

package com.example.iksystem.repository;

import com.example.iksystem.entity.UsersEntity;
import com.example.iksystem.enums.model.constant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Bu arayüz User Repository davranışlarını tanımlar.
 */
//Bu arayüz, UsersEntity ile ilgili veritabanı işlemlerini gerçekleştirmek için JpaRepository'yi genişletir. UUID türünde birincil anahtar kullanır.
public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

    // Kullanıcı e-posta adresinin var olup olmadığını kontrol eder (büyük/küçük harf duyarsız).
    boolean existsByEmailIgnoreCase(String email);

    // Belirli bir e-posta adresine sahip olan ve belirli bir ID'ye sahip olmayan kullanıcı var mı diye kontrol eder (büyük/küçük harf duyarsız).
    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);

    // Belirli bir e-posta adresine sahip kullanıcıyı döndürür (büyük/küçük harf duyarsız).
    Optional<UsersEntity> findByEmailIgnoreCase(String email);

    // Belirli bir role sahip tüm kullanıcıları döndürür.
    List<UsersEntity> findAllByRole(Role role);
    List<UsersEntity> findAllByIsActive(Boolean isActive);

}

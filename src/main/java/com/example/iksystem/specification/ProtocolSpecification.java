
package com.example.iksystem.specification;

import com.example.iksystem.entity.ProtocolsEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;
/**
 * Bu sınıf Protocol Specification nesnesini temsil eder.
 */

public class ProtocolSpecification {
    private ProtocolSpecification() {
    }

    // Bu metod, protokolün aktif ve süresi dolmamış olup olmadığını kontrol eden bir Specification döndürür.
    public static Specification<ProtocolsEntity> isActiveAndNotExpired() {

        return (root, query, criteriaBuilder) -> { // Eğer protokol aktif ve süresi dolmamış ise true döndürür.
            Predicate isActive = criteriaBuilder.equal(root.get("protocolStatus"), "ACTIVE"); // Protokolün aktif olması gerekmektedir.
            Predicate isNotExpired = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now()); // Protokolün bitiş tarihi bugünden büyük veya eşit olmalıdır.
            return criteriaBuilder.and(isActive, isNotExpired);

        };

    }

    // Bu metod, protokolün başlığında veya şirket adında belirtilen anahtar kelimeyi içeren bir Specification döndürür.
    public static Specification<ProtocolsEntity> containsKeyword(String keyword) {

        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String pattern = "%" + keyword.toLowerCase(Locale.ENGLISH) + "%";
            Predicate titleLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);// Protokol başlığı anahtar kelimeyi içeriyorsa true döndürür.
            Predicate companyNameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("company").get("companyName")), pattern); // Şirket adı anahtar kelimeyi içeriyorsa true döndürür.
            return criteriaBuilder.or(titleLike, companyNameLike);


        };
    }

    // Bu metod, protokolün kategori kimliğinin belirtilen kategori kimliği ile eşleşip eşleşmediğini kontrol eden bir Specification döndürür.
    public static Specification<ProtocolsEntity> hasCategoryId(UUID categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }
}


package com.example.iksystem.specification;

import com.example.iksystem.ProtocolsEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class ProtocolSpecification {
    private ProtocolSpecification() {
    }

    public static Specification<ProtocolsEntity> isActiveAndNotExpired() {

        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.equal(root.get("protocolStatus"), true);
            Predicate isNotExpired = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now());
            return criteriaBuilder.and(isActive, isNotExpired);

        };

    }

    public static Specification<ProtocolsEntity> containsKeyword(String keyword) {

        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String pattern = "%" + keyword.toLowerCase(Locale.ENGLISH) + "%";
            Predicate titleLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);
            Predicate companyNameLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("company").get("name")), pattern);
            return criteriaBuilder.or(titleLike, companyNameLike);


        };
    }

    public static Specification<ProtocolsEntity> hasCategoryId(UUID categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }
}



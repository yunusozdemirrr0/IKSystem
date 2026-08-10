package com.example.iksystem.specification;

import com.example.iksystem.ProtocolsEntity;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Locale;

public class ProtocolSpecification {
    private ProtocolSpecification() {
}
public static Specification<ProtocolsEntity> isActiveAndNotExpired(){

        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.equal(root.get("protocolStatus"), true);
            Predicate isNotExpired = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now());
            return criteriaBuilder.and(isActive, isNotExpired);

        };

}

public static Specification<ProtocolsEntity>containsKeyword(String keyword){

        return (root, query, criteriaBuilder1) -> {
            if (!StringUtils.hasText(keyword)){
                return null;
            }
            String pattern = "%" + keyword.toLowerCase(Locale.ENGLISH) + "%";
            Predicate titleLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);

            return criteriaBuilder1.or(titleLike)
            );

        }



}



}

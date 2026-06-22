package com.poncheck.service.impl;

import com.poncheck.entity.Product;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> withFilters(Long businessId, Long categoryId, PoncheBase ponchebase, ProductSize size){
        return(root, query, cb) -> {
            List<Predicate> predicates= new ArrayList<>();

            predicates.add(cb.equal(root.get("business").get("id"), businessId));
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (ponchebase != null) {
                predicates.add(cb.equal(root.get("poncheBase"), ponchebase));
            }

            if (size != null) {
                predicates.add(cb.equal(root.get("productSize"), size));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

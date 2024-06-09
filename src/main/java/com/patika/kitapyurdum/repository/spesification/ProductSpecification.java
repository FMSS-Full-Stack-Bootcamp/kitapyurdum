package com.patika.kitapyurdum.repository.spesification;

import com.patika.kitapyurdum.dto.request.ProductSearchRequest;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.model.Publisher;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecification {

    public static Specification<Product> initProductSpecification(ProductSearchRequest request) {
        return (root, query, criteriaBuilder) -> {

            Join<Product, Publisher> publisherJoin = root.join("publisher");

            List<Predicate> predicateList = new ArrayList<>();

            if (request.getName() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("name"), request.getName())); //like ile aramaya çevirin
            }

            if (request.getAmount() != null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("amount"), request.getAmount()));
            }

            if (request.getPublisherName() != null) {
                predicateList.add(criteriaBuilder.like(publisherJoin.get("name"), "%" + request.getPublisherName() + "%"));
            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}

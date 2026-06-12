package co.istad.productapisimpledemo.specification;

import co.istad.productapisimpledemo.dto.request.ProductSearchToRequest;
import co.istad.productapisimpledemo.entity.Product;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static <T> Specification<T> like(String field, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get(field)),
                        "%" + value.toLowerCase() + "%"
                );
    }

    public static <T> Specification<T> greaterThan(String field, Comparable value) {
        if (value == null) {
            return null;
        }

        return (root, query, cb) ->
                cb.greaterThan(root.get(field), value);
    }

    public static <T> Specification<T> likeJoin(String joinField, String field, String value) {
        if (value == null || value.trim().isBlank()) return null;
        return (root, query, cb) -> {
            root.fetch(joinField, JoinType.LEFT); // ✅ explicit join
            return cb.like(
                    cb.lower(root.join(joinField, JoinType.LEFT).get(field)),
                    "%" + value.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> fromRequest(ProductSearchToRequest request) {
        Specification<Product> spec = Specification.unrestricted();

        if (request.name() != null && !request.name().isBlank()) {
            spec = spec.and(like("name", request.name()));
        }
        if (request.description() != null && !request.description().isBlank()) {
            spec = spec.and(like("description", request.description()));
        }
        if (request.categoryName() != null && !request.categoryName().isBlank()) {
            spec = spec.and(likeJoin("category", "name", request.categoryName()));
        }

        return spec;
    }

}
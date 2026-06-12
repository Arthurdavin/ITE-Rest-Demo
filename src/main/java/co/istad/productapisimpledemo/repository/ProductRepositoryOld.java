//public class ProductSpecification {
//
//    public static Specification<Product> hasName(String name) {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("name"), name);
//    }
//
//    public static Specification<Product> priceGreaterThan(Double price) {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.greaterThan(
//                        root.get("price"),
//                        price
//                );
//    }
//}
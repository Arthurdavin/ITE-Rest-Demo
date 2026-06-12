package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.ProductSearchToRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.entity.Product;
import co.istad.productapisimpledemo.mapper.ProductMapper;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import co.istad.productapisimpledemo.repository.ProductRepository;

import co.istad.productapisimpledemo.service.ProductService;
import co.istad.productapisimpledemo.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(()-> new NoSuchElementException("Category not found with ID: "+ request.categoryId()));

        var product = productMapper.productRequestToProduct(request);
        product.setUsrId(1);
        product.setCategory(category);

        return productMapper.productToProductResponse(productRepository.save(product));
    }

    //
    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductResponse)// yk tv map oy vea jea product response because when it response is response product simple
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Integer id,
                                         UpdateProductRequest request) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID: " + id));

        if (request.name() != null) {
            existingProduct.setName(request.name());
        }

        if (request.description() != null) {
            existingProduct.setDescription(request.description());
        }

        if (request.price() != null) {
            existingProduct.setPrice(request.price());
        }

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.productToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer id) {

        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("product with id = "+ id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductByID(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID: " + id));

        return productMapper.productToProductResponse(product);
    }

    @Override
    public List<ProductResponse> searchProducts(
            String name,
            Double minPrice
    ) {
        Specification<Product> spec = Specification.unrestricted();
        if (name != null && !name.isBlank()) {
            spec = spec.and(
                    ProductSpecification.like(
                            "name",
                            name
                    )
            );
        }
        if (minPrice != null) {
            spec = spec.and(
                    ProductSpecification.greaterThan(
                            "price",
                            minPrice
                    )
            );
        }
        return productRepository.findAll(spec)
                .stream()
                .map(productMapper::productToProductResponse)
                .toList();
    }

    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::productToProductResponse);
    }

    @Override
    public Page<ProductResponse> search(String keywords, Pageable pageable) {
        Specification<Product> spec = Specification.unrestricted();

        if (keywords != null && !keywords.isBlank()) {
            Specification<Product> byName = ProductSpecification.like("name", keywords);
            Specification<Product> byDesc = ProductSpecification.like("description", keywords);
            Specification<Product> byCategory = ProductSpecification.likeJoin("category", "name", keywords);

            spec = spec.and(byName.or(byDesc).or(byCategory));
        }

        return productRepository.findAll(spec, pageable)
                .map(productMapper::productToProductResponse);
    }

    @Override
    public Page<ProductResponse> name(ProductSearchToRequest request, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.fromRequest(request);
        return productRepository.findAll(spec, pageable)
                .map(productMapper::productToProductResponse);
    }

}

//@Service
//public class BaseSpecification<T> {
//    public static class SpecsDto {
// --.
//    }
//    public static class FilterDto {
// --.
//    }
//}
//public static class SpecsDto {
//    private String column;
//    private String value;
//    private String joinTable;
//    private Operation operation;
//    public enum Operation{
//        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, BETWEEN, JOIN;
//    }
//}
//public static class FilterDto {
//    private List<SpecsDto> specsDto;
//    private GlobalOperator globalOperator;
//    public enum GlobalOperator{
//        AND, OR;
//    }
//}
//@Service
//public class BaseSpecification<T> {
// -/ For single specification -| you might use lambda expression
//    public Specification<T> filter(SpecsDto specsDto) {
//        return new Specification<T>() {
//            @Override
//            public Predicate toPredicate(Root<T> root, CriteriaQuery<?>
//                    query, CriteriaBuilder builder) {
//                return builder.equal(root.get(specsDto.getColumn()),
//                        specsDto.getValue());
//            }
//        };
//    }
//}
//@Service
//public class BaseSpecification<T> {
//    public Specification<T> filter(List<SpecsDto> specsDto, FilterDto.GlobalOperator
//            globalOperator) {
//        return (root, query, builder) -> {
//            List<Predicate> predicates = new ArrayList->();
//            for (SpecsDto specs : specsDto) {
//                -/ Build your predicate here--.
//            }
//            if (globalOperator.equals(FilterDto.GlobalOperator.AND)) {
//                return builder.and(predicates.toArray(new Predicate[0]));
//            } else {
//                return builder.or(predicates.toArray(new Predicate[0]));
//            }
//        };
//    }}
//switch (specs.getOperation()) {
//        case EQUAL -> {
//Predicate equal = builder.equal(root.get(specs.getColumn()), specs.getValue());
// predicates.add(equal);
// }
//         case LIKE -> {
//Predicate like = builder.like(root.get(specs.getColumn()), "%"+specs.getValue()+"%");
// predicates.add(like);
// }
//         case JOIN -> {
//Predicate join = builder.equal(root.join(specs.getJoinTable()).get(specs.getColumn()),
//        specs.getValue());
// predicates.add(join);
// }
//default -> throw new IllegalStateException("Unexpected value: ");
//}
//        switch (specs.getOperation()) {
//        case IN -> {
//String[] split = specs.getValue().split(",");
//Predicate in = root.get(specs.getColumn()).in(Arrays.asList(split));
// predicates.add(in);
//}
//        case GREATER_THAN -> {
//Predicate greaterThan = builder.greaterThan(root.get(specs.getColumn()), specs.getValue());
// predicates.add(greaterThan);
//}
//        case BETWEEN -> {
//String[] split1 = specs.getValue().split(",");
//Predicate between = builder.between(root.get(specs.getColumn()), Long.parseLong(split1[0]),
//        Long.parseLong(split1[1]));
// predicates.add(between);
//}
//default -> throw new IllegalStateException("Unexpected value: ");
//}
//@RestController
//@RequestMapping("/api/students")
//@RequiredArgsConstructor
//public class StudentController {
//    private final StudentRepository studentRepository;
//    private final BaseSpecification<Student> baseSpecification;
//    @PostMapping
//    List<StudentDto> findAll(@RequestBody BaseSpecification.FilterDto filterDto) {
//        Specification<Student> specs =
//                baseSpecification.filter(filterDto.getSearchRequestDto(),
//                        filterDto.getGlobalOperator());
//        var students = studentRepository.findAll(specs);
//        return students.stream()
//                .map(student -> StudentDto.builder().name(student.getName()).build())
//                .toList();
//    }
//}

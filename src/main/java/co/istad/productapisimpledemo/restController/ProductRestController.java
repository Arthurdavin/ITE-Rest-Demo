package co.istad.productapisimpledemo.restController;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest createProductRequest){
        return productService.createProduct(createProductRequest);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(productService.findAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ProductResponse getProductByID(@PathVariable Integer id){
        return productService.getProductByID(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice
    ) {
        return productService.searchProducts(
                name,
                minPrice
        );
    }

    @GetMapping("/searchkeyword")
    public Page<ProductResponse> search(
            @RequestParam(required = false) String keyword,
            @ParameterObject Pageable pageable
    ){
        return productService.search(keyword,pageable);
    }
}





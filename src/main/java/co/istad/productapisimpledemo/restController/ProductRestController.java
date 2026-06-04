package co.istad.productapisimpledemo.restController;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public List<ProductResponse> getAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductByID(@PathVariable Integer id){
        return productService.getProductByID(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }
}

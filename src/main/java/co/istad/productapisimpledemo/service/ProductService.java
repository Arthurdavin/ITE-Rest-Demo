package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.ProductSearchToRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest createProductRequest);
    List<ProductResponse> findAllProducts();
    ProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest);
    void deleteProduct(Integer id);
    ProductResponse getProductByID(Integer id);
    List<ProductResponse> searchProducts(
            String name,
            Double minPrice
    );
    Page<ProductResponse> findAllProducts(Pageable pageable);

    Page<ProductResponse> search(String keywords, Pageable pageable);

    Page<ProductResponse> name(ProductSearchToRequest request, Pageable pageable);
}

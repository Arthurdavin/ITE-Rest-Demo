package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;

import java.util.List;


public interface ProductService {
    ProductResponse createProduct(CreateProductRequest createProductRequest);
    List<ProductResponse> findAllProducts();
    ProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest);
    boolean deleteProduct(Integer id);
    ProductResponse getProductByID(Integer id);
}

package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.entity.Product;
import co.istad.productapisimpledemo.repository.ProductRepository;
import co.istad.productapisimpledemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    //inject the repository here

    private final ProductRepository productRepository;

    private int nextId = 4;
    //ប្រើនៅពេលដែលយើងទាញទិន្នន័យពី Database
    // បានជា Entity ហើយចង់បញ្ជូនទៅ Client ជា Response DTO។
    private ProductResponse mapToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    //ប្រើនៅពេលដែល Client ផ្ញើ Request មក
    // Server ហើយយើងចង់បម្លែង Request DTO
    // ទៅជា Entity មុននឹង Save ចូល Database។
    private Product mapToEntity(CreateProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        var product = mapToEntity(request);
        product.setUsrId(1);
        product.setId(nextId++);
        return mapToResponse(productRepository.createProduct(product));
    }
//
    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepository.getAllProduct()
                .stream()
                .map(this::mapToResponse)// yk tv map oy vea jea product response because when it response is response product simple
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest request) {
        Product exitProduct = productRepository.getProductByID(id);
        if (exitProduct == null){
//            log.info("product not found: {}",id);
            throw new RuntimeException("Product not found with ID: "+id);
        }
        if(request.name()!=null){
            exitProduct.setName(request.name());
        }
        if (request.description()!=null){
            exitProduct.setDescription(request.description());
        }
        if (request.price()!=null){
            exitProduct.setPrice(request.price());
        }

        // update product

        Product updateProduct = productRepository.updateProduct(exitProduct);

        return mapToResponse(updateProduct);
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return productRepository.deleteProductByID(id);
    }

    @Override
    public ProductResponse getProductByID(Integer id) {
        Product product = productRepository.getProductByID(id);
        return mapToResponse(product);
    }
}

package co.istad.productapisimpledemo.mapper;

import co.istad.productapisimpledemo.dto.request.CreateProductRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {CategoryMapper.class})
public interface ProductMapper {
    Product productRequestToProduct(CreateProductRequest createProductRequest);
    ProductResponse productToProductResponse(Product product);
}

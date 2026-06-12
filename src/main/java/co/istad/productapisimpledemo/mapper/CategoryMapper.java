package co.istad.productapisimpledemo.mapper;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CategoryMapper {
    CategoryResponse categoryEntityToCategoryResponse(Category category);
    Category categoryToRequest(CreateCategoryRequest categoryRequest);
}

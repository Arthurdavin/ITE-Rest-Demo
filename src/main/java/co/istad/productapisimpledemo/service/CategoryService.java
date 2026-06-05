package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse updateCategoryById(Integer id, UpdateProductRequest updateProductRequest);
    Boolean deleteCategoryById(Integer id);
}

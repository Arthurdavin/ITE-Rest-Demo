package co.istad.productapisimpledemo.service;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);
    Page<CategoryResponse> getAllCategories(int page, int size);
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);
    void deleteCategoryById(Integer id);
}

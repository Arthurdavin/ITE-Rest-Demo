package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import co.istad.productapisimpledemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private int nextId = 4;

    private CategoryResponse mapToResponse(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    private Category mapToEntity(CreateCategoryRequest request){
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return category;
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        var category = mapToEntity(request);
        category.setId(nextId++);
        return mapToResponse(categoryRepository.createCategory(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository
                .getCategories()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.getCategoryById(id);
        return mapToResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateProductRequest request) {

        Category exitCategory = categoryRepository.getCategoryById(id);
        if (exitCategory == null){
            throw new RuntimeException("Category not found with Id:"+id);
        }
        if (request.name()!=null){
            exitCategory.setName(request.name());
        }
        if (request.description()!=null){
            exitCategory.setDescription(request.description());
        }

        Category updateCategory = categoryRepository.updateCategoryById(exitCategory);

        return mapToResponse(updateCategory);
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        return categoryRepository.deleteCategoryById(id);
    }
}

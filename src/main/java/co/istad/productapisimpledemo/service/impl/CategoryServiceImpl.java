package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.advisor.ResourceAlreadyExistException;
import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.mapper.CategoryMapper;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import co.istad.productapisimpledemo.repository.CategoryRepositoryOld;
import co.istad.productapisimpledemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

//    private CategoryResponse mapToResponse(Category category){
//        return new CategoryResponse(
//                category.getId(),
//                category.getName(),
//                category.getDescription()
//        );
//    }
//
//    private Category mapToEntity(CreateCategoryRequest request){
//        Category category = new Category();
//        category.setName(request.name());
//        category.setDescription(request.description());
//        return category;
//    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = categoryMapper.categoryToRequest(request);
        if (categoryRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistException(
                   "category with name: "+ request.name()+" already exists"
            );
        }
        var newCategory = categoryRepository.save(category);
        return categoryMapper.categoryEntityToCategoryResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
//        Category category =
//        categoryMapper.categoryToCategoryEntity();
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::categoryEntityToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("category not fount"));
        return categoryMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest request) {

        Category exitCategory =
                categoryRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("category not found"));
        if (request.name()!=null){
            exitCategory.setName(request.name());
        }
        if (request.description()!=null){
            exitCategory.setDescription(request.description());
        }

        Category updateCategory = categoryRepository.save(exitCategory);

        return categoryMapper.categoryEntityToCategoryResponse(updateCategory);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("category with id = " + id));

        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}

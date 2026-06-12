package co.istad.productapisimpledemo.service.impl;

import co.istad.productapisimpledemo.advisor.ResourceAlreadyExistException;
import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateCategoryRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;
import co.istad.productapisimpledemo.entity.Category;
import co.istad.productapisimpledemo.mapper.CategoryMapper;
import co.istad.productapisimpledemo.repository.CategoryRepository;
import co.istad.productapisimpledemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

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
    public Page<CategoryResponse> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository
                .findAllByIsDeletedFalse(pageable)
                .map(categoryMapper::categoryEntityToCategoryResponse);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Category with id = "+ id + "not found"
                ));
        return categoryMapper.categoryEntityToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest request) {

        Category exitCategory =
                categoryRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        if (request.name()!=null){
            if (categoryRepository.existsByNameAndIdNot(request.name(),id)){
                throw new ResourceAlreadyExistException(
                        "Category with name: "+ request.name() + " already exists"
                );
            }
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
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoSuchElementException("category with id = " + id));

        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}

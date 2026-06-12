package co.istad.productapisimpledemo.restController;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateCategoryRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;

import co.istad.productapisimpledemo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor

public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponse> getAllCategories(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        return categoryService.getAllCategories(page,size);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id){
        return  categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @RequestBody UpdateCategoryRequest request){
        return categoryService.updateCategoryById(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable Integer id
    ) {
        categoryService.deleteCategoryById(id);
    }
}

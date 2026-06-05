package co.istad.productapisimpledemo.restController;

import co.istad.productapisimpledemo.dto.request.CreateCategoryRequest;
import co.istad.productapisimpledemo.dto.request.UpdateProductRequest;
import co.istad.productapisimpledemo.dto.response.CategoryResponse;

import co.istad.productapisimpledemo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catories")
@RequiredArgsConstructor

public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
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
    public CategoryResponse updateCategory(@PathVariable Integer id, @RequestBody UpdateProductRequest request){
        return categoryService.updateCategoryById(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory (@PathVariable Integer id){
        boolean isDeleted = categoryService.deleteCategoryById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}

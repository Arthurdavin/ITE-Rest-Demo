package co.istad.productapisimpledemo.repository;

import co.istad.productapisimpledemo.entity.Category;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Repository

public class CategoryRepositoryOld {

    private final List<Category> categories = new ArrayList<>(){{
//        add(new Category(1,"Dring","jeat mg hah",true));
//        add(new Category(2,"Food","jeat mg hah",true));
//        add(new Category(3,"Computer","jeat mg hah",true));
    }};

    public List<Category> getCategories() {
        return categories;
    }
    public Category getCategoryById(Integer id){
        return categories.stream()
                .filter(c-> Objects.equals(c.getId(), id))
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("Category not found"+ id+" Not Found!"));
    }

    public Category createCategory(Category category){
        categories.add(category);
        return category;
    }
    public Boolean deleteCategoryById(Integer id){
        return categories.removeIf(c-> Objects.equals(c.getId(), id));
    }

    public Category updateCategoryById(Category updateCategory){
        for (int i = 0;i<categories.size();i++){
            var category = categories.get(i);
            if (Objects.equals(category.getId(), updateCategory.getId())){
                categories.set(i,updateCategory);
                return updateCategory;
            }
        }
        return null;
    }

}

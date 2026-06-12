package co.istad.productapisimpledemo.repository;

import co.istad.productapisimpledemo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Boolean existsByName(String name);
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);
    Page<Category> findAllByIsDeletedFalse(Pageable pageable);
    Boolean existsByNameAndIdNot(String name, Integer id);
}

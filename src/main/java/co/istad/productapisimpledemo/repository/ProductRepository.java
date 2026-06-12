package co.istad.productapisimpledemo.repository;

import co.istad.productapisimpledemo.dto.request.ProductSearchToRequest;
import co.istad.productapisimpledemo.dto.response.ProductResponse;
import co.istad.productapisimpledemo.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer>,
        JpaSpecificationExecutor<Product> {
}

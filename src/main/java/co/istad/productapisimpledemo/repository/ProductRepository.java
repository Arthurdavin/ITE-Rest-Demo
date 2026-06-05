package co.istad.productapisimpledemo.repository;

import co.istad.productapisimpledemo.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>() {{
        add(new Product(1, "Coca", "Cut jeang", 11.4f, 1));
        add(new Product(2, "String", "Cut jeang", 11.11f, 2));
        add(new Product(3, "Fanta", "Cut jeang", 11.9f, 3));
    }};

    //    public Optional<Product> getProductByID(int id) {
//        return products.stream().filter(product -> product.getId() == id)
//                .findFirst()
//                ;
//    }

    public Product getProductByID(Integer id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(()->new NoSuchElementException("Product not found! "+id+" Not found!"));//NoSuchElementException
    }

    public List<Product> getAllProduct() {
        return this.products;
    }


    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    public boolean deleteProductByID(Integer id) {
        return products.removeIf(product -> product.getId() == id);
    }

//    public Product updateProduct(Product updateProduct) {
//        for (int i = 0; i < products.size(); i++) {
//            var product = products.get(i);
//            if (product.getId() == updateProduct.getId()) {
//                products.set(i, updateProduct);
//                return updateProduct;
//            }
//        }
//        return null;
//    }
    public Product updateProduct(Product updateProduct){
        for (int i = 0;i<products.size();i++){
            var product = products.get(i);
            if (product.getId() == updateProduct.getId()){
                products.set(i,updateProduct);
                return updateProduct;
            }
        }
        return null;
    }

}

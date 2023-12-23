package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Category;
import pl.janiuk.shopapi.domain.Product;
import pl.janiuk.shopapi.domain.ProductCategories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product create(String name, String description, double price, Collection<ProductCategories> categories);
    Optional<Product> single(int id);
    List<Product> list();
    Optional<Product> addCategory(int productId, int categoryId);
    Optional<Product> removeCategory(int productId, int categoryId);
    void delete(int id);
    boolean existsById(int id);
}

package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Category;
import pl.janiuk.shopapi.domain.Product;
import pl.janiuk.shopapi.domain.ProductCategories;
import pl.janiuk.shopapi.domain.ProductCategoriesPK;
import pl.janiuk.shopapi.repository.CategoryRepository;
import pl.janiuk.shopapi.repository.ProductCategoriesRepository;
import pl.janiuk.shopapi.repository.ProductRepository;
import pl.janiuk.shopapi.service.IProductService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final ProductCategoriesRepository productCategoriesRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoriesRepository productCategoriesRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productCategoriesRepository = productCategoriesRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product create(String name, String description, double price, Collection<ProductCategories> categories) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .productCategoriesByProductId(categories)
                .availability(0)
                .build();
        return productRepository.save(product);
    }
    @Override
    public Optional<Product> single(int id) {
        return productRepository.findById(id);
    }
    @Override
    public List<Product> list() {
        return productRepository.findAll();
    }
    @Override
    public Optional<Product> addCategory(int productId, int categoryId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get();
            Category category = optionalCategory.get();
            ProductCategories productCategories = ProductCategories.builder()
                    .productId(product.getId())
                    .categoryId(category.getId())
                    .productByProductId(product)
                    .categoryByCategoryId(category)
                    .build();
            productCategoriesRepository.save(productCategories);
            return productRepository.findById(productId);
        }
        return Optional.empty();
    }
    @Override
    public Optional<Product> removeCategory(int productId, int categoryId) {
        ProductCategoriesPK productCategoriesPK = ProductCategoriesPK.builder()
                .productId(productId)
                .categoryId(categoryId)
                .build();
        productCategoriesRepository.deleteById(productCategoriesPK);
        return productRepository.findById(productId);
    }
    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return productRepository.existsById(id);
    }
}

package pl.janiuk.shopapi.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.IProductController;
import pl.janiuk.shopapi.domain.Category;
import pl.janiuk.shopapi.domain.Product;
import pl.janiuk.shopapi.domain.ProductCategories;
import pl.janiuk.shopapi.dto.category.CategoryResponse;
import pl.janiuk.shopapi.dto.product.CreateProductRequest;
import pl.janiuk.shopapi.dto.product.ProductResponse;
import pl.janiuk.shopapi.service.ICategoryService;
import pl.janiuk.shopapi.service.IProductService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements IProductController {
    IProductService productService;
    ICategoryService categoryService;
    @Autowired
    public ProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest request) {
        Collection<ProductCategories> categories = Collections.emptyList();
        Product product = productService.create(request.name(), request.description(), request.price(), categories);
        ProductResponse response = productToProductResponse(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<ProductResponse> singleProduct(int id) {
        Optional<Product> foundProduct = productService.single(id);
        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            ProductResponse response = productToProductResponse(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<List<ProductResponse>> listProducts() {
        List<Product> foundProducts = productService.list();
        List<ProductResponse> response = foundProducts.stream().map(this::productToProductResponse).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> deleteProduct(int id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Override
    public ResponseEntity<ProductResponse> addCategoryToProduct(int productId, int categoryId) {
        boolean productExists = productService.existsById(productId);
        boolean categoryExists = categoryService.existsById(categoryId);
        if (productExists && categoryExists) {
            Optional<Product> optionalProduct = productService.addCategory(productId, categoryId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                ProductResponse response = productToProductResponse(product);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<ProductResponse> removeCategoryFromProduct(int productId, int categoryId) {
        boolean productExists = productService.existsById(productId);
        boolean categoryExists = categoryService.existsById(categoryId);
        if (productExists && categoryExists) {
            Optional<Product> optionalProduct = productService.removeCategory(productId, categoryId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                ProductResponse response = productToProductResponse(product);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private ProductResponse productToProductResponse(Product product) {
        Collection<ProductCategories> productCategories = product.getProductCategoriesByProductId();
        List<Category> categoryList = productCategories.stream().map(ProductCategories::getCategoryByCategoryId).toList();
        List<CategoryResponse> categories = categoryList.stream().map(category -> new CategoryResponse(
                category.getId(),
                category.getName()
        )).toList();
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailability(),
                product.getPrice(),
                categories
        );
    }
}

package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.product.CreateProductRequest;
import pl.janiuk.shopapi.dto.product.ProductResponse;

import java.util.List;

@RequestMapping("/api/v1/product")
public interface IProductController {
    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request);
    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> singleProduct(@PathVariable int id);
    @GetMapping
    ResponseEntity<List<ProductResponse>> listProducts();
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable int id);
    @PostMapping("/{productId}/{categoryId}")
    ResponseEntity<ProductResponse> addCategoryToProduct(@PathVariable int productId, @PathVariable int categoryId);
    @DeleteMapping("/{productId}/{categoryId}")
    ResponseEntity<ProductResponse> removeCategoryFromProduct(@PathVariable int productId, @PathVariable int categoryId);
}

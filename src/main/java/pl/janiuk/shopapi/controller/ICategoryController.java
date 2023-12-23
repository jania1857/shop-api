package pl.janiuk.shopapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.janiuk.shopapi.dto.category.CreateCategoryRequest;
import pl.janiuk.shopapi.dto.category.CategoryResponse;

import java.util.List;

@RequestMapping("/api/v1/category")
public interface ICategoryController {
    @PostMapping
    ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request);
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable int id);
    @GetMapping
    ResponseEntity<List<CategoryResponse>> listCategories();
}

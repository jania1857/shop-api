package pl.janiuk.shopapi.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.janiuk.shopapi.controller.ICategoryController;
import pl.janiuk.shopapi.domain.Category;
import pl.janiuk.shopapi.dto.category.CreateCategoryRequest;
import pl.janiuk.shopapi.dto.category.CategoryResponse;
import pl.janiuk.shopapi.service.ICategoryService;

import java.util.List;

@RestController
public class CategoryController implements ICategoryController {
    ICategoryService categoryService;
    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Override
    public ResponseEntity<CategoryResponse> createCategory(CreateCategoryRequest request) {
        Category category = categoryService.create(request.name());
        CategoryResponse response = new CategoryResponse(
                category.getId(),
                category.getName()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteCategory(int id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        List<Category> categoryList = categoryService.list();
        List<CategoryResponse> categoryResponseList = categoryList.stream().map(category -> new CategoryResponse(
                category.getId(),
                category.getName()
        )).toList();
        return new ResponseEntity<>(categoryResponseList, HttpStatus.OK);
    }
}

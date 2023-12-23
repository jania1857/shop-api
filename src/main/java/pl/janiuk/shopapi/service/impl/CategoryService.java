package pl.janiuk.shopapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.janiuk.shopapi.domain.Category;
import pl.janiuk.shopapi.repository.CategoryRepository;
import pl.janiuk.shopapi.service.ICategoryService;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category create(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return categoryRepository.save(category);
    }
    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }
}

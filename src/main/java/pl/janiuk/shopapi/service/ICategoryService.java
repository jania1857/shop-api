package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Category;

import java.util.List;

public interface ICategoryService {
    Category create(String name);
    void delete(int id);
    List<Category> list();
}

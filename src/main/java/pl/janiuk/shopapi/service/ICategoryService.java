package pl.janiuk.shopapi.service;

import pl.janiuk.shopapi.domain.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category create(String name);
    void delete(int id);
    List<Category> list();
    boolean existsById(int id);
    Optional<Category> single(int id);
}

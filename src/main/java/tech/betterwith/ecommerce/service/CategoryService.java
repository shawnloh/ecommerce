package tech.betterwith.ecommerce.service;

import tech.betterwith.ecommerce.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(UUID categoryId);

    Category updateCategory(Category category, UUID categoryId);
}

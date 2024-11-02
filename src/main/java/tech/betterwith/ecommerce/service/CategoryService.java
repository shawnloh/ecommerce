package tech.betterwith.ecommerce.service;

import tech.betterwith.ecommerce.model.Category;
import tech.betterwith.ecommerce.payload.CategoryResponseDTO;

import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO getAllCategories();

    void createCategory(Category category);

    String deleteCategory(UUID categoryId);

    Category updateCategory(Category category, UUID categoryId);
}

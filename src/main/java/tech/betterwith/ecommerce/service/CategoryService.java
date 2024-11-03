package tech.betterwith.ecommerce.service;

import tech.betterwith.ecommerce.payload.CategoryDTO;
import tech.betterwith.ecommerce.payload.CategoryResponseDTO;

import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(UUID categoryId);

    CategoryDTO updateCategory(CategoryDTO category, UUID categoryId);
}

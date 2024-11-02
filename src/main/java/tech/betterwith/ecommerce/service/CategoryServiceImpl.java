package tech.betterwith.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.betterwith.ecommerce.exceptions.APIException;
import tech.betterwith.ecommerce.exceptions.ResourceNotFoundException;
import tech.betterwith.ecommerce.model.Category;
import tech.betterwith.ecommerce.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Optional<Category> savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory.isPresent()) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        categoryRepository.delete(category);
        return "Category deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, UUID categoryId) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);
        Category existingCategory = savedCategory.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }
}

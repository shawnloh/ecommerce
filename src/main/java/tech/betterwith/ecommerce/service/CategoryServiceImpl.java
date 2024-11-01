package tech.betterwith.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(UUID categoryId) {
//        categories.removeIf(category -> category.getCategoryId().equals(categoryId));
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            return "Category not found";
        }
        categoryRepository.delete(category.get());
        return "Category deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, UUID categoryId) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);

        if (savedCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

        Category existingCategory = savedCategory.get();
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return existingCategory;

    }
}

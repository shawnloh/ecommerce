package tech.betterwith.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.betterwith.ecommerce.exceptions.APIException;
import tech.betterwith.ecommerce.exceptions.ResourceNotFoundException;
import tech.betterwith.ecommerce.model.Category;
import tech.betterwith.ecommerce.payload.CategoryDTO;
import tech.betterwith.ecommerce.payload.CategoryResponseDTO;
import tech.betterwith.ecommerce.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponseDTO getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        return new CategoryResponseDTO(categoryDTOS);
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

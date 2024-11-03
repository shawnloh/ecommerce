package tech.betterwith.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryResponseDTO getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categories = categoryRepository.findAll(pageDetails);
//        List<Category> categories = categoryRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber)).toList();
//        List<Category> categories = categoryRepository.findAll(pageDetails).toList();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        List<CategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        return new CategoryResponseDTO(categoryDTOS, categories.getNumber(), categories.getSize(), categories.getTotalElements(), categories.getTotalPages(), categories.isLast());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new APIException("Category with the name " + categoryDTO.getCategoryName() + " already exists");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO category, UUID categoryId) {
        Optional<Category> savedCategory = categoryRepository.findById(categoryId);
        Category existingCategory = savedCategory.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }
}

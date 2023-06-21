package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.TypeOfCategoryConstant;
import com.example.moneymanagerbe.domain.dto.request.CategoryRequestDto;
import com.example.moneymanagerbe.domain.dto.response.CategoryResponseDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Category;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.CategoryMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.InvalidException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.repository.CategoryRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UserRepository userRepository;

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{id});
                });
    }

    @Override
    public CategoryResponseDto createNew(CategoryRequestDto categoryRequestDto) {

        User user = userRepository.findById(categoryRequestDto.getUserId())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                            new String[]{categoryRequestDto.getUserId()});
                });

        List<Category> categories = categoryRepository.findCategoriesByUserId(categoryRequestDto.getUserId());
        categories.forEach(category -> {
            if(category.getName().equals(categoryRequestDto.getName())) {
                throw new AlreadyExistException(ErrorMessage.Category.ERR_ALREADY_EXIST_NAME,
                        new String[]{categoryRequestDto.getName()});
            }
        });

        if (!categoryRequestDto.getType().equals(TypeOfCategoryConstant.EXPENSE) &&
                !categoryRequestDto.getType().equals(TypeOfCategoryConstant.INCOME)) {
            throw new InvalidException(ErrorMessage.Category.INVALID_CATEGORY_TYPE);
        }

        Category category = categoryMapper.toCategory(categoryRequestDto);
        category.setUser(user);
        categoryRepository.save(category);
        return categoryMapper.toResponseDto(category);
    }

    @Override
    public CommonResponseDto delete(String id, String userId) {
        this.getById(id);
        if (this.getCategoryIdByUser(userId).contains(id)) {
            categoryRepository.deleteById(id);
            return new CommonResponseDto(true, "Deleted");
        }
        return new CommonResponseDto(false, ErrorMessage.Budget.ERR_NOT_FOUND_ID);
    }

    @Override
    public List<Category> getCategoriesByUser(String userId) {
        return categoryRepository.findCategoriesByUserId(userId);
    }

    @Override
    public List<String> getCategoryIdByUser(String userId) {
        List<String> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findCategoriesByUserId(userId);
        for (Category category : categories) result.add(category.getId());
        return result;
    }
}

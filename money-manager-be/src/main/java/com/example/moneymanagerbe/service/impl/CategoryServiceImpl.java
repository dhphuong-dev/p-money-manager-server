package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.domain.dto.request.CategoryRequestDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Category;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.CategoryMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
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
    public Category createNew(CategoryRequestDto categoryRequestDto) {

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

        Category category = categoryMapper.toCategory(categoryRequestDto);
        category.setUser(user);
        return categoryRepository.save(category);
    }

    @Override
    public CommonResponseDto delete(String id, String userId) {
        return null;
    }

    @Override
    public List<Category> getCategoriesByUser(String userId) {
        return categoryRepository.findCategoriesByUserId(userId);
    }

    @Override
    public List<String> getCategoryIdByUser(String userId) {
        List<String> result = new ArrayList<>();
        List<Category> categories = categoryRepository.findCategoriesByUserId(userId);
        categories.forEach(category -> {
            result.add(category.getId());
        });
        return result;
    }
}

package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.MessageConstant;
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
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.CategoryRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.service.CategoryService;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final UserService userService;

    private final UploadFileUtil uploadFileUtil;

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Category.ERR_NOT_FOUND_ID, new String[]{id});
                });
    }

    @Override
    public CategoryResponseDto createNew(String userId, CategoryRequestDto categoryRequestDto) {

        User user = userService.getUserById(userId);

        List<Category> categories = categoryRepository.findCategoriesByUserId(userId);
        for (Category c : categories) {
            if (c.getName().equals(categoryRequestDto.getName())) {
                throw new AlreadyExistException(ErrorMessage.Category.ERR_ALREADY_EXIST_NAME,
                        new String[]{categoryRequestDto.getName()});
            }
        }

        Category category = categoryMapper.toCategory(categoryRequestDto);
        category.setUser(user);

        if (categoryRequestDto.getImage() != null) {
            String imageUrl = uploadFileUtil.uploadFile(categoryRequestDto.getImage());
            category.setImageUrl(imageUrl);
        }

        categoryRepository.save(category);
        return categoryMapper.toResponseDto(category);
    }

    @Override
    public CommonResponseDto delete(String id, String userId) {
        Category category = this.getById(id);

        List<Category> categories = categoryRepository.findCategoriesByUserId(userId);
        if (categories.contains(category)) {
            uploadFileUtil.destroyFileWithUrl(category.getImageUrl());
            categoryRepository.delete(category);
            return new CommonResponseDto(true, MessageConstant.DELETED);
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
    }

    @Override
    public List<Category> getCategoriesByUser(String userId) {
        return categoryRepository.findCategoriesByUserId(userId);
    }

    @Override
    public List<CategoryResponseDto> getCategoriesDtoByUser(String userId) {
        return categoryMapper.toListResponseDto(this.getCategoriesByUser(userId));
    }

}

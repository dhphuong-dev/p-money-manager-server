package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.request.CategoryRequestDto;
import com.example.moneymanagerbe.domain.dto.response.CategoryResponseDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createNew(CategoryRequestDto categoryRequestDto);

    CommonResponseDto delete(String id, String userId);

    List<Category> getCategoriesByUser(String userId);

    List<String> getCategoryIdByUser(String userId);

}

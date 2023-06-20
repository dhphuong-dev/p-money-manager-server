package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.request.CategoryRequestDto;
import com.example.moneymanagerbe.domain.dto.response.CategoryResponseDto;
import com.example.moneymanagerbe.domain.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequestDto categoryRequestDto);

    @Mappings({
            @Mapping(target = "userId", source = "category.user.id")
    })
    CategoryResponseDto toResponseDto(Category category);
}

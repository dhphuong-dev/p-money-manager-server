package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.response.BudgetResponseDto;
import com.example.moneymanagerbe.domain.entity.Budget;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    BudgetResponseDto toResponseDto(Budget budget);
}

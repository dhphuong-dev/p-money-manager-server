package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.request.BudgetRequestDto;
import com.example.moneymanagerbe.domain.dto.response.BudgetResponseDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Budget;

import java.util.List;

public interface BudgetService {

    Budget getById(String id);

    BudgetResponseDto createNewBudget(BudgetRequestDto budgetRequestDto);

    BudgetResponseDto updateBudgetName(String id, String name, String userId);

    BudgetResponseDto updateBudgetTotal(String id, float total, String userId);

    CommonResponseDto deleteBudget(String id, String userId);

    List<BudgetResponseDto> getBudgetsByUser(String userId);

    List<String> getIdByUser(String userId);
}

package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.domain.dto.request.BudgetRequestDto;
import com.example.moneymanagerbe.domain.dto.response.BudgetResponseDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Budget;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.BudgetMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.exception.OutOfBoundException;
import com.example.moneymanagerbe.repository.BudgetRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetMapper budgetMapper;

    private final UserRepository userRepository;

    @Override
    public Budget getById(String id) {
        return budgetRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(ErrorMessage.Budget.ERR_NOT_FOUND_ID, new String[]{id});
        });
    }

    @Override
    public BudgetResponseDto createNewBudget(BudgetRequestDto budgetRequestDto) {
        if(budgetRepository.findAll().size() >= 2) {
            throw new OutOfBoundException(ErrorMessage.Budget.ERR_FULL_BUDGET);
        }

        User user = userRepository.findById(budgetRequestDto.getUserId())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                            new String[]{budgetRequestDto.getUserId()});
                });

        List<Budget> budgets = budgetRepository.findBudgetsByUser(budgetRequestDto.getUserId());
        for (Budget b : budgets) {
            if(b.getName().equals(budgetRequestDto.getName())) {
                throw new AlreadyExistException(ErrorMessage.Budget.ERR_ALREADY_EXIST_NAME,
                        new String[]{budgetRequestDto.getName()});
            }
        }

        Budget budget = new Budget();
        budget.setName(budgetRequestDto.getName());
        budget.setTotal(0);
        budget.setUser(user);
        budgetRepository.save(budget);
        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public BudgetResponseDto updateBudgetName(String id, String name, String userId) {
        Budget budget = this.getById(id);
        if(!name.equals(budget.getName())) {
            List<String> ids = this.getIdByUser(userId);
            if(ids.contains(id)) {
                budget.setName(name);
                budgetRepository.save(budget);
            }
        }
        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public BudgetResponseDto updateBudgetTotal(String id, float total, String userId) {
        Budget budget = this.getById(id);
        if(budget.getTotal() != total) {
            List<String> ids = this.getIdByUser(userId);
            if(ids.contains(id)) {
                budget.setTotal(total);
                budgetRepository.save(budget);
            }
        }
        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public CommonResponseDto deleteBudget(String id, String userId) {
        this.getById(id);
        List<String> ids = this.getIdByUser(userId);
        if(ids.contains(id)) {
            budgetRepository.deleteById(id);
            return new CommonResponseDto(true, "Deleted");
        }
        return new CommonResponseDto(false, ErrorMessage.Budget.ERR_NOT_FOUND_ID);
    }

    @Override
    public List<BudgetResponseDto> getBudgetsByUser(String userId) {
        List<Budget> budgets = budgetRepository.findBudgetsByUser(userId);
        List<BudgetResponseDto> result = new ArrayList<>();
        budgets.forEach((budget) -> {
            result.add(budgetMapper.toResponseDto(budget));
        });
        return result;
    }

    @Override
    public List<String> getIdByUser(String userId) {
        List<Budget> budgets = budgetRepository.findBudgetsByUser(userId);
        List<String> ids = new ArrayList<>();
        budgets.forEach((budget) -> {
            ids.add(budget.getId());
        });
        return ids;
    }
}

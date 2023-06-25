package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.CommonConstant;
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
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.BudgetRepository;
import com.example.moneymanagerbe.repository.UserRepository;
import com.example.moneymanagerbe.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        budget.setTotal(CommonConstant.ZERO_INT_VALUE);
        budget.setUser(user);
        budgetRepository.save(budget);
        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public BudgetResponseDto updateBudgetName(String id, String name, String userId) {
        Budget budget = this.getById(id);

        List<Budget> budgets = budgetRepository.findBudgetsByUser(userId);
        if(budgets.contains(budget)) {
            if(!name.equals(budget.getName())) {
                budget.setName(name);
                budgetRepository.save(budget);
            }
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);

        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public BudgetResponseDto updateBudgetTotal(String id, float total, String userId) {
        Budget budget = this.getById(id);

        List<Budget> budgets = budgetRepository.findBudgetsByUser(userId);
        if(budgets.contains(budget)) {
            if(total != budget.getTotal()) {
                budget.setTotal(total);
                budgetRepository.save(budget);
            }
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);

        return budgetMapper.toResponseDto(budget);
    }

    @Override
    public CommonResponseDto deleteBudget(String id, String userId) {
        Budget budget = this.getById(id);

        List<Budget> budgets = budgetRepository.findBudgetsByUser(userId);
        if(budgets.contains(budget)) {
            budgetRepository.deleteById(id);
            return new CommonResponseDto(true, "Deleted");
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
    }

    @Override
    public List<Budget> getBudgetsByUser(String userId) {
        return budgetRepository.findBudgetsByUser(userId);
    }

    @Override
    public List<BudgetResponseDto> getBudgetsDtoByUser(String userId) {
        return budgetMapper.toListResponseDto(this.getBudgetsByUser(userId));
    }

}

package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.request.BudgetRequestDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class BudgetController {

    private final BudgetService budgetService;

    @Tag(name = "budget-controller")
    @Operation(summary = "API create new budget")
    @PostMapping(UrlConstant.Budget.POST_NEW_BUDGET)
    public ResponseEntity<?> createNewBudget(@Valid @RequestBody BudgetRequestDto budgetRequestDto) {
        return VsResponseUtil.success(budgetService.createNewBudget(budgetRequestDto));
    }

    @Tag(name = "budget-controller")
    @Operation(summary = "API update name budget")
    @PostMapping(UrlConstant.Budget.UPDATE_NAME_BUDGET)
    public ResponseEntity<?> updateBudgetName(@PathVariable String id, @RequestParam String name,
                                              @Parameter(name = "user", hidden = true)
                                              @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(budgetService.updateBudgetName(id, name, user.getId()));
    }

    @Tag(name = "budget-controller")
    @Operation(summary = "API delete budget")
    @DeleteMapping(UrlConstant.Budget.DELETE_BUDGETS)
    public ResponseEntity<?> deleteBudget(@PathVariable String id,
                                          @Parameter(name = "user", hidden = true)
                                          @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(budgetService.deleteBudget(id, user.getId()));
    }

    @Tag(name = "budget-controller")
    @Operation(summary = "API get budgets of current user")
    @GetMapping(UrlConstant.Budget.GET_BUDGETS)
    public ResponseEntity<?> getBudgetsByUser(@Parameter(name = "user", hidden = true)
                                              @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(budgetService.getBudgetsByUser(user.getId()));
    }
}

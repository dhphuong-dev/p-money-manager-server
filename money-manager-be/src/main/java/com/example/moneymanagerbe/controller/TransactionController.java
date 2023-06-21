package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class TransactionController {

    private final TransactionService transactionService;

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS)
    public ResponseEntity<?> getTransactionsByUser(@Valid @ParameterObject PaginationRequestDto paginationRequestDto,
                                                   @Parameter(name = "user", hidden = true)
                                                   @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.getTransactionsByUser(paginationRequestDto, user.getId()));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API create new transaction")
    @PostMapping(UrlConstant.Transaction.POST_TRANSACTION)
    public ResponseEntity<?> createNewTransaction(@Valid @RequestBody TransactionCreateDto transactionCreateDto,
                                                  @Parameter(name = "user", hidden = true)
                                                  @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.createNew(transactionCreateDto));
    }
}

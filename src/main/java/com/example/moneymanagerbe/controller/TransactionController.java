package com.example.moneymanagerbe.controller;

import com.example.moneymanagerbe.base.RestApiV1;
import com.example.moneymanagerbe.base.VsResponseUtil;
import com.example.moneymanagerbe.constant.UrlConstant;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.security.CurrentUser;
import com.example.moneymanagerbe.security.UserPrincipal;
import com.example.moneymanagerbe.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class TransactionController {

    private final TransactionService transactionService;

//    @Tag(name = "transaction-controller")
//    @Operation(summary = "API get transactions by current user")
//    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS_BY_CURRENT_USER)
//    public ResponseEntity<?> getTransactionsByUser(@Valid @ParameterObject PaginationFullRequestDto paginationRequestDto,
//                                                   @Parameter(name = "user", hidden = true)
//                                                   @CurrentUser UserPrincipal user) {
//        return VsResponseUtil.success(transactionService.getTransactionsByUser(paginationRequestDto, user.getId()));
//    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS_BY_CURRENT_USER)
    public ResponseEntity<?> getTransactionsByUser(@Parameter(name = "user", hidden = true)
                                                   @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.getTransactionsByUser(user.getId()));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTION_BY_ID)
    public ResponseEntity<?> getTransactionsById(@RequestParam String id,
                                                   @Parameter(name = "user", hidden = true)
                                                   @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.getTransactionById(id));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by wallet of current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS_BY_WALLET)
    public ResponseEntity<?> getTransactionsByUserAndWallet(@Valid @ParameterObject PaginationFullRequestDto paginationRequestDto,
                                                            @Parameter(name = "user", hidden = true)
                                                            @CurrentUser UserPrincipal user,
                                                            @PathVariable String walletId) {
        return VsResponseUtil.success(transactionService.getTransactionsByUserAndWallet(paginationRequestDto,
                user.getId(), walletId));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by category of current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS_BY_CATEGORY)
    public ResponseEntity<?> getTransactionsByUserAndCategory(@Valid @ParameterObject PaginationFullRequestDto paginationRequestDto,
                                                            @Parameter(name = "user", hidden = true)
                                                            @CurrentUser UserPrincipal user,
                                                            @PathVariable String categoryId) {
        return VsResponseUtil.success(transactionService.getTransactionsByUserAndCategory(paginationRequestDto,
                user.getId(), categoryId));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API get transactions by category of current user")
    @GetMapping(UrlConstant.Transaction.GET_TRANSACTIONS_BY_CATEGORY_TYPE)
    public ResponseEntity<?> getTransactionsByUserAndCategoryType(@Valid @ParameterObject PaginationFullRequestDto paginationRequestDto,
                                                              @Parameter(name = "user", hidden = true)
                                                              @CurrentUser UserPrincipal user,
                                                              @PathVariable String type) {
        return VsResponseUtil.success(transactionService.getTransactionsByUserAndCategoryType(paginationRequestDto,
                user.getId(), type));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API create new transaction")
    @PostMapping(value = UrlConstant.Transaction.POST_TRANSACTION, consumes = "multipart/form-data")
    public ResponseEntity<?> createNewTransaction(@Valid @ModelAttribute TransactionCreateDto transactionCreateDto,
                                                  @Parameter(name = "user", hidden = true)
                                                  @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.createNew(user.getId(), transactionCreateDto));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API update transaction by id")
    @PatchMapping(value = UrlConstant.Transaction.PATCH_TRANSACTION, consumes = "multipart/form-data")
    public ResponseEntity<?> updateById(@PathVariable String id,
                                        @Valid @ModelAttribute TransactionUpdateDto transactionUpdateDto,
                                        @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.updateById(id, transactionUpdateDto));
    }

    @Tag(name = "transaction-controller")
    @Operation(summary = "API delete transaction by id")
    @DeleteMapping(UrlConstant.Transaction.DELETE_TRANSACTION)
    public ResponseEntity<?> deleteById(@PathVariable String id,
                                        @Parameter(name = "user", hidden = true)
                                        @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(transactionService.deleteById(id, user.getId()));
    }
}

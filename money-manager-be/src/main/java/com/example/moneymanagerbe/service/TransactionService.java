package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.pagination.PaginationRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction getById(String id);

    TransactionResponseDto createNew(TransactionCreateDto transactionCreateDto);

    TransactionResponseDto updateById(String id, TransactionUpdateDto transactionUpdateDto);

    CommonResponseDto deleteById(String id, String userId);

    PaginationResponseDto<TransactionResponseDto> getTransactions(PaginationRequestDto paginationRequestDto);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUser(PaginationRequestDto paginationRequestDto, String userId);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndBudget(PaginationRequestDto paginationRequestDto, String userId, String budgetId);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategory(PaginationRequestDto paginationRequestDto, String userId, String categoryId);

//    List<TransactionResponseDto> getTransactionsByUserAndCategory(String userId, String categoryId);
}

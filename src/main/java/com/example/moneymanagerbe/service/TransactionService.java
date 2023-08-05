package com.example.moneymanagerbe.service;

import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Transaction;

public interface TransactionService {

    Transaction getById(String id);

    TransactionResponseDto createNew(String userId, TransactionCreateDto transactionCreateDto);

    TransactionResponseDto updateById(String id, TransactionUpdateDto transactionUpdateDto);

    CommonResponseDto deleteById(String id, String userId);

    PaginationResponseDto<TransactionResponseDto> getTransactions(PaginationFullRequestDto paginationRequestDto);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUser(PaginationFullRequestDto paginationRequestDto, String userId);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndWallet(PaginationFullRequestDto paginationRequestDto, String userId, String walletId);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategory(PaginationFullRequestDto paginationRequestDto, String userId, String categoryId);

    PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategoryType(PaginationFullRequestDto paginationRequestDto, String userId, String type);

//    List<TransactionResponseDto> getTransactionsByUserAndCategory(String userId, String categoryId);
}

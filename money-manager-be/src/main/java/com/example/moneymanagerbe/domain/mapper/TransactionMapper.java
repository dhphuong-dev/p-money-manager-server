package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toTransaction(TransactionCreateDto transactionRequestDto);

    Transaction toTransaction(TransactionUpdateDto transactionUpdateDto);

    @Mappings({
            @Mapping(target = "categoryId", source = "transaction.category.id"),
            @Mapping(target = "budgetId", source = "transaction.budget.id"),
    })
    TransactionResponseDto toResponseDto(Transaction transaction);

    List<TransactionResponseDto> toListResponseDto(List<Transaction> transactions);
}

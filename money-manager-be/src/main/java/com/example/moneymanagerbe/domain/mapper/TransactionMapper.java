package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Transaction;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    @Mapping(target = "imageUrl", ignore = true)
    Transaction toTransaction(TransactionCreateDto transactionRequestDto);

    @Mapping(target = "imageUrl", ignore = true)
    void updateTransaction(@MappingTarget Transaction transaction, TransactionUpdateDto transactionUpdateDto);

    @Mappings({
            @Mapping(target = "categoryId", source = "transaction.category.id"),
            @Mapping(target = "budgetId", source = "transaction.budget.id"),
    })
    TransactionResponseDto toResponseDto(Transaction transaction);

    List<TransactionResponseDto> toListResponseDto(List<Transaction> transactions);
}

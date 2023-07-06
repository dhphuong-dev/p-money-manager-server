package com.example.moneymanagerbe.domain.mapper;

import com.example.moneymanagerbe.domain.dto.response.WalletResponseDto;
import com.example.moneymanagerbe.domain.entity.Wallet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponseDto toResponseDto(Wallet wallet);

    List<WalletResponseDto> toListResponseDto(List<Wallet> wallets);
}

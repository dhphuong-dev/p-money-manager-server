package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.CommonConstant;
import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.MessageConstant;
import com.example.moneymanagerbe.domain.dto.request.WalletRequestDto;
import com.example.moneymanagerbe.domain.dto.response.WalletResponseDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.entity.Wallet;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.WalletMapper;
import com.example.moneymanagerbe.exception.AlreadyExistException;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.exception.OutOfBoundException;
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.WalletRepository;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    private final UserService userService;

    @Override
    public Wallet getById(String id) {
        return walletRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(ErrorMessage.Wallet.ERR_NOT_FOUND_ID, new String[]{id});
        });
    }

    @Override
    public WalletResponseDto createNewWallet(String userId, WalletRequestDto walletRequestDto) {
        if(walletRepository.findAll().size() >= 2) {
            throw new OutOfBoundException(ErrorMessage.Wallet.ERR_FULL_WALLET);
        }

        User user = userService.getUserById(userId);

        List<Wallet> wallets = walletRepository.findWalletsByUser(userId);


        for (Wallet b : wallets) {
            if(b.getName().equals(walletRequestDto.getName())) {
                throw new AlreadyExistException(ErrorMessage.Wallet.ERR_ALREADY_EXIST_NAME,
                        new String[]{walletRequestDto.getName()});
            }
        }

        Wallet wallet = new Wallet();
        wallet.setName(walletRequestDto.getName());
        wallet.setTotal(CommonConstant.ZERO_INT_VALUE);
        wallet.setUser(user);
        walletRepository.save(wallet);
        return walletMapper.toResponseDto(wallet);
    }

    @Override
    public WalletResponseDto updateWalletName(String id, String name, String userId) {
        Wallet wallet = this.getById(id);

        List<Wallet> wallets = walletRepository.findWalletsByUser(userId);
        if(wallets.contains(wallet)) {
            if(!name.equals(wallet.getName())) {
                wallet.setName(name);
                walletRepository.save(wallet);
            }
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);

        return walletMapper.toResponseDto(wallet);
    }

    @Override
    public WalletResponseDto updateWalletTotal(String id, float total, String userId) {
        Wallet wallet = this.getById(id);

        List<Wallet> wallets = walletRepository.findWalletsByUser(userId);
        if(wallets.contains(wallet)) {
            if(total != wallet.getTotal()) {
                wallet.setTotal(total);
                walletRepository.save(wallet);
            }
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);

        return walletMapper.toResponseDto(wallet);
    }

    @Override
    public CommonResponseDto deleteWallet(String id, String userId) {
        Wallet wallet = this.getById(id);

        List<Wallet> wallets = walletRepository.findWalletsByUser(userId);
        if(wallets.contains(wallet)) {
            walletRepository.deleteById(id);
            return new CommonResponseDto(true, MessageConstant.DELETED);
        } else throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
    }

    @Override
    public List<Wallet> getWalletsByUser(String userId) {
        return walletRepository.findWalletsByUser(userId);
    }

    @Override
    public List<WalletResponseDto> getWalletsDtoByUser(String userId) {
        return walletMapper.toListResponseDto(this.getWalletsByUser(userId));
    }

}

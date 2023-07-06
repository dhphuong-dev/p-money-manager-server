package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.MessageConstant;
import com.example.moneymanagerbe.constant.SortByDataConstant;
import com.example.moneymanagerbe.constant.TypeOfCategoryConstant;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationFullRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.pagination.PagingMeta;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Wallet;
import com.example.moneymanagerbe.domain.entity.Category;
import com.example.moneymanagerbe.domain.entity.Transaction;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.TransactionMapper;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.TransactionRepository;
import com.example.moneymanagerbe.service.WalletService;
import com.example.moneymanagerbe.service.CategoryService;
import com.example.moneymanagerbe.service.TransactionService;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.util.PaginationUtil;
import com.example.moneymanagerbe.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final UserService userService;

    private final WalletService walletService;

    private final CategoryService categoryService;

    private final UploadFileUtil uploadFileUtil;

    @Override
    public Transaction getById(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Transaction.ERR_NOT_FOUND_ID,
                            new String[]{id});
                });
    }

    @Override
    public TransactionResponseDto createNew(TransactionCreateDto transactionCreateDto) {
        User user = userService.getUserById(transactionCreateDto.getUserId());
        Wallet wallet = walletService.getById(transactionCreateDto.getWalletId());
        Category category = categoryService.getById(transactionCreateDto.getCategoryId());

        Transaction transaction = transactionMapper.toTransaction(transactionCreateDto);

        if (category.getType().equals(TypeOfCategoryConstant.INCOME))
            transaction.setTotal(transactionCreateDto.getTotal());
        else
            transaction.setTotal(-transactionCreateDto.getTotal());

        walletService.updateWalletTotal(wallet.getId(),
                transaction.getTotal() + wallet.getTotal(), user.getId());

        transaction.setUser(user);
        transaction.setWallet(wallet);
        transaction.setCategory(category);

        if (transactionCreateDto.getImage() != null) {
            String imageUrl = uploadFileUtil.uploadFile(transactionCreateDto.getImage());
            transaction.setImageUrl(imageUrl);
        }

        transactionRepository.save(transaction);

        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public TransactionResponseDto updateById(String id, TransactionUpdateDto transactionUpdateDto) {
        Transaction transaction = this.getById(id);

        float oldTotal = transaction.getTotal();

        transactionMapper.updateTransaction(transaction, transactionUpdateDto);

        if (transactionUpdateDto.getTotal() != 0) {
            if (transaction.getCategory().getType().equals(TypeOfCategoryConstant.INCOME))
                transaction.setTotal( transactionUpdateDto.getTotal() );
            else
                transaction.setTotal( -transactionUpdateDto.getTotal() );
        } else transaction.setTotal(oldTotal);

        if (transactionUpdateDto.getImage() != null) {
            String newImageUrl = uploadFileUtil.uploadFile(transactionUpdateDto.getImage());
            String oldImageUrl = transaction.getImageUrl();
            uploadFileUtil.destroyFileWithUrl(oldImageUrl);
            transaction.setImageUrl(newImageUrl);
        }

        return transactionMapper.toResponseDto(transactionRepository.save(transaction));
    }

    @Override
    public CommonResponseDto deleteById(String id, String userId) {
        Transaction transaction =  this.getById(id);

        List<Transaction> transactions = transactionRepository.findTransactionsByUser(userId);

        if (transactions.contains(transaction)) {
            uploadFileUtil.destroyFileWithUrl(transaction.getImageUrl());
            transactionRepository.delete(transaction);
            return new CommonResponseDto(true, MessageConstant.DELETED);
        }

        throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactions(PaginationFullRequestDto paginationRequestDto) {
        return null;
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUser(
            PaginationFullRequestDto paginationRequestDto, String userId) {
        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto, SortByDataConstant.Transaction);

        Page<Transaction> page = transactionRepository.findTransactionsByUser(userId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto,
                SortByDataConstant.Transaction, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndWallet(
            PaginationFullRequestDto paginationRequestDto, String userId, String walletId) {
        Wallet wallet = walletService.getById(walletId);

        if (!walletService.getWalletsByUser(userId).contains(wallet)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto, SortByDataConstant.Transaction);

        Page<Transaction> page = transactionRepository.findTransactionsByUserAndWallet(userId, walletId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto,
                SortByDataConstant.Transaction, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategory(
            PaginationFullRequestDto paginationRequestDto, String userId, String categoryId) {
        Category category = categoryService.getById(categoryId);

        if (!categoryService.getCategoriesByUser(userId).contains(category)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto, SortByDataConstant.Transaction);

        Page<Transaction> page = transactionRepository.findTransactionsByUserAndCategory(userId, categoryId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto,
                SortByDataConstant.Transaction, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategoryType(
            PaginationFullRequestDto paginationRequestDto, String userId, String type) {
        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto, SortByDataConstant.Transaction);

        if (type.equalsIgnoreCase(TypeOfCategoryConstant.INCOME))
            type = TypeOfCategoryConstant.INCOME;
        else if (type.equalsIgnoreCase(TypeOfCategoryConstant.EXPENSE)) {
            type = TypeOfCategoryConstant.EXPENSE;
        }

        Page<Transaction> page = transactionRepository.findTransactionsByUserAndCategoryType(userId, type, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto,
                SortByDataConstant.Transaction, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }
}

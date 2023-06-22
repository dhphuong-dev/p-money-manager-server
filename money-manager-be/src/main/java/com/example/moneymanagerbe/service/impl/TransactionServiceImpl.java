package com.example.moneymanagerbe.service.impl;

import com.example.moneymanagerbe.constant.ErrorMessage;
import com.example.moneymanagerbe.constant.TypeOfCategoryConstant;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationRequestDto;
import com.example.moneymanagerbe.domain.dto.pagination.PaginationResponseDto;
import com.example.moneymanagerbe.domain.dto.pagination.PagingMeta;
import com.example.moneymanagerbe.domain.dto.request.TransactionCreateDto;
import com.example.moneymanagerbe.domain.dto.request.TransactionUpdateDto;
import com.example.moneymanagerbe.domain.dto.response.CommonResponseDto;
import com.example.moneymanagerbe.domain.dto.response.TransactionResponseDto;
import com.example.moneymanagerbe.domain.entity.Budget;
import com.example.moneymanagerbe.domain.entity.Category;
import com.example.moneymanagerbe.domain.entity.Transaction;
import com.example.moneymanagerbe.domain.entity.User;
import com.example.moneymanagerbe.domain.mapper.TransactionMapper;
import com.example.moneymanagerbe.exception.NotFoundException;
import com.example.moneymanagerbe.exception.UnauthorizedException;
import com.example.moneymanagerbe.repository.TransactionRepository;
import com.example.moneymanagerbe.service.BudgetService;
import com.example.moneymanagerbe.service.CategoryService;
import com.example.moneymanagerbe.service.TransactionService;
import com.example.moneymanagerbe.service.UserService;
import com.example.moneymanagerbe.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final UserService userService;

    private final BudgetService budgetService;

    private final CategoryService categoryService;

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
        Budget budget = budgetService.getById(transactionCreateDto.getBudgetId());
        Category category = categoryService.getById(transactionCreateDto.getCategoryId());

        Transaction transaction = transactionMapper.toTransaction(transactionCreateDto);

        if (category.getType().equals(TypeOfCategoryConstant.INCOME))
            transaction.setTotal(transactionCreateDto.getTotal());
        else
            transaction.setTotal(-transactionCreateDto.getTotal());

        budgetService.updateBudgetTotal(budget.getId(),
                transaction.getTotal() + budget.getTotal(), user.getId());

        transaction.setUser(user);
        transaction.setBudget(budget);
        transaction.setCategory(category);

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

        return transactionMapper.toResponseDto(transactionRepository.save(transaction));
    }

    @Override
    public CommonResponseDto deleteById(String id, String userId) {
        Transaction transaction =  this.getById(id);

        List<Transaction> transactions = transactionRepository.getTransactionsByUser(userId);

        if (transactions.contains(transaction)) {
            transactionRepository.delete(transaction);
            return new CommonResponseDto(true, "Deleted");
        }

        throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactions(PaginationRequestDto paginationRequestDto) {
        return null;
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUser(PaginationRequestDto paginationRequestDto, String userId) {
        User user = userService.getUserById(userId);

        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto);

        Page<Transaction> page = transactionRepository.getTransactionsByUser(userId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndBudget(PaginationRequestDto paginationRequestDto, String userId, String budgetId) {
        User user = userService.getUserById(userId);

        Budget budget = budgetService.getById(budgetId);

        if (!budgetService.getBudgetsByUser(userId).contains(budget)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto);

        Page<Transaction> page = transactionRepository.getTransactionsByUserAndBudget(userId, budgetId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }

    @Override
    public PaginationResponseDto<TransactionResponseDto> getTransactionsByUserAndCategory(PaginationRequestDto paginationRequestDto, String userId, String categoryId) {
        User user = userService.getUserById(userId);

        Category category = categoryService.getById(categoryId);

        if (!categoryService.getCategoriesByUser(userId).contains(category)) {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN);
        }

        Pageable pageable = PaginationUtil.buildPageable(paginationRequestDto);

        Page<Transaction> page = transactionRepository.getTransactionsByUserAndCategory(userId, categoryId, pageable);

        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(paginationRequestDto, page);

        return new PaginationResponseDto<>(pagingMeta,
                transactionMapper.toListResponseDto(page.toList()));
    }
}

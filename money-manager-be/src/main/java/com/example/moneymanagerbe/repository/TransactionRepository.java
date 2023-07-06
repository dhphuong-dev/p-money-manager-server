package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1")
    List<Transaction> findTransactionsByUser(String userId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1")
    Page<Transaction> findTransactionsByUser(String userId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1 AND t.budget.id=?2")
    List<Transaction> findTransactionsByUserAndBudget(String userId, String budgetId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1 AND t.budget.id=?2")
    Page<Transaction> findTransactionsByUserAndBudget(String userId, String budgetId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1 AND t.category.id=?2")
    List<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id=?1 AND t.category.id=?2")
    Page<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId, Pageable pageable);
}

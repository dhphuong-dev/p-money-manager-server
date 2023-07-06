package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1", nativeQuery = true)
    List<Transaction> findTransactionsByUser(String userId);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1", nativeQuery = true)
    Page<Transaction> findTransactionsByUser(String userId, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.budget_id = ?2",
            nativeQuery = true)
    List<Transaction> findTransactionsByUserAndBudget(String userId, String budgetId);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.budget_id = ?2",
            nativeQuery = true)
    Page<Transaction> findTransactionsByUserAndBudget(String userId, String budgetId, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.category_id = ?2",
            nativeQuery = true)
    List<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.category_id = ?2",
            nativeQuery = true)
    Page<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId, Pageable pageable);
}

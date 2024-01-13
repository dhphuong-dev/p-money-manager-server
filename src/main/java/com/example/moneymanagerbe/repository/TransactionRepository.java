package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1")
    List<Transaction> findTransactionsByUser(String userId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1")
    Page<Transaction> findTransactionsByUser(String userId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1 AND t.wallet.id = ?2")
    List<Transaction> findTransactionsByUserAndWallet(String userId, String walletId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1 AND t.wallet.id = ?2")
    Page<Transaction> findTransactionsByUserAndWallet(String userId, String walletId, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1 AND t.category.id = ?2")
    List<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = ?1 AND t.category.id = ?2")
    Page<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId, Pageable pageable);

    @Query("SELECT t FROM Transaction t JOIN Category c " +
            "ON t.category.id = c.id WHERE t.user.id = ?1 AND UPPER(c.type) = UPPER(?2)")
    List<Transaction> findTransactionsByUserAndCategoryType(String userId, String type);

    @Query("SELECT t FROM Transaction t JOIN Category c " +
            "ON t.category.id = c.id WHERE t.user.id = ?1 AND UPPER(c.type) = UPPER(?2)")
    Page<Transaction> findTransactionsByUserAndCategoryType(String userId, String type, Pageable pageable);

}

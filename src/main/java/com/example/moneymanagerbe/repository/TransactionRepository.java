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

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.wallet_id = ?2",
            nativeQuery = true)
    List<Transaction> findTransactionsByUserAndWallet(String userId, String walletId);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.wallet_id = ?2",
            nativeQuery = true)
    Page<Transaction> findTransactionsByUserAndWallet(String userId, String walletId, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.category_id = ?2",
            nativeQuery = true)
    List<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId);

    @Query(value = "SELECT t.* FROM transactions t WHERE t.user_id = ?1 AND t.category_id = ?2",
            nativeQuery = true)
    Page<Transaction> findTransactionsByUserAndCategory(String userId, String categoryId, Pageable pageable);

    @Query(value = "SELECT t.* FROM transactions t JOIN categories c " +
            "ON t.category_id = c.id WHERE t.user_id = ?1 AND c.type COLLATE utf8_bin = ?2",
            nativeQuery = true)
    List<Transaction> findTransactionsByUserAndCategoryType(String userId, String type);

    @Query(value = "SELECT t.* FROM transactions t JOIN categories c " +
            "ON t.category_id = c.id WHERE t.user_id = ?1 AND c.type COLLATE utf8_bin = ?2",
            nativeQuery = true)
    Page<Transaction> findTransactionsByUserAndCategoryType(String userId, String type, Pageable pageable);

}

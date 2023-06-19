package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, String> {

    @Query("SELECT b FROM Wallet b WHERE b.user.id=?1")
    List<Wallet> findWalletsByUser(String userId);
}

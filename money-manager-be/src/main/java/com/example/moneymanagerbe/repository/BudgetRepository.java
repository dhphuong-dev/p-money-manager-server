package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, String> {

    @Query("SELECT b FROM Budget b WHERE b.user.id=?1")
    List<Budget> findBudgetsByUser(String userId);
}

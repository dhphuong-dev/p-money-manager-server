package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, String> {
}

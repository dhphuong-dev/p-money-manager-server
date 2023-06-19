package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}

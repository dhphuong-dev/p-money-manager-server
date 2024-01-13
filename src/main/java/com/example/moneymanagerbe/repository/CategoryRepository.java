package com.example.moneymanagerbe.repository;

import com.example.moneymanagerbe.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.user.id = ?1")
    List<Category> findCategoriesByUserId(String userId);
}

package com.example.demo123.repository;

import com.example.demo123.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);

    String query = "select * from categories where categories.id = 1";
    @Query(value = query,nativeQuery = true)
    List<Category> showCategoryByIT();
}

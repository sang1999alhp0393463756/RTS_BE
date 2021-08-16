package com.example.demo123.repository;

import com.example.demo123.dto.request.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRequestRepository extends JpaRepository<CategoryRequest, Long> {

    String query = "select * from categories where status like 'active';";
    @Query(value = query,nativeQuery = true)
    List<CategoryRequest> showAllCategory();
}

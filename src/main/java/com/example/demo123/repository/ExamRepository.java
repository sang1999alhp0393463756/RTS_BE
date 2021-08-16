package com.example.demo123.repository;

import com.example.demo123.dto.response.examRespon;
import com.example.demo123.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query(value = "SELECT * FROM rts.exam where id =?",nativeQuery = true)
    Optional<examRespon> findExamDetail(Long id);
    @Query(value = "SELECT * FROM rts.exam where course_id =?",nativeQuery = true)
    List<examRespon> getAllByCourse(Long course_id);
}

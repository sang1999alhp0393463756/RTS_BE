package com.example.demo123.repository;

import com.example.demo123.dto.response.examPublic;
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
    @Query(value = "SELECT a.id as examId,a.created_by,a.created_date,a.security,a.title,a.course_id,b.title as courseName,c.name as categoryName,c.id as category_id FROM rts.exam a inner join rts.courses b on a.course_id = b.id inner join rts.categories c on b.category_id = c.id where b.status like 'active' and a.security like 'public'",nativeQuery = true)
    List<examPublic> getExamByPublic();
}

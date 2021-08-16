package com.example.demo123.repository;

import com.example.demo123.entity.question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<question,Long> {
    @Query(value = "SELECT * FROM rts.questions where exam_id = ?",nativeQuery = true)
    List<question> getQuestionByExam(Integer exam_id);
    @Modifying
    @Transactional
    @Query(value = "delete from rts.questions where exam_id =?",nativeQuery = true)
    void DeleteByExam(Long exam_id);
}

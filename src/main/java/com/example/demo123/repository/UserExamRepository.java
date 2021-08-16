package com.example.demo123.repository;

import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;

import com.example.demo123.entity.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

public interface UserExamRepository extends JpaRepository<UserExam,Long> {
  @Query(value = "SELECT * FROM rts.user_exam where exam_id =? and user_id=?;",nativeQuery = true)
    List<UserExam> getResultListTest(Long examId,Long userId);
  @Query(value = "SELECT * FROM rts.user_exam where id=? and exam_id =? and user_id=?;",nativeQuery = true)
  List<UserExam> getResultDetailTest(Long id,Long examId,Long userId);
}

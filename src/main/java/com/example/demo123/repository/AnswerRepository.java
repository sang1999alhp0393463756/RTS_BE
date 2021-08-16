package com.example.demo123.repository;

import com.example.demo123.dto.response.resultTestDetail;
import com.example.demo123.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    @Query(value = "SELECT b.id,b.correctanswer,b.number,b.option1,b.option2,b.option3,b.option4,b.question,a.answer As userAnswer,a.is_correct,a.exam_id,a.test_id,a.user_id FROM rts.answers a inner join rts.questions b on a.question_id = b.id where a.test_id =? and a.user_id =? and a.exam_id =?",nativeQuery = true)
    List<resultTestDetail> resultTestDetail(Long test_id,Long user_id,Long exam_id);
}

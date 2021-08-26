package com.example.demo123.controller.client;

import com.example.demo123.dto.response.examPublic;
import com.example.demo123.dto.response.userRegisterCourse;
import com.example.demo123.entity.question;
import com.example.demo123.repository.ExamRepository;
import com.example.demo123.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client/exam")
public class examPublicController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ExamRepository examRepository;

    @GetMapping("/examPublicList")
    public ResponseEntity<?> examPublicList(){
        List<examPublic> list = examRepository.getExamByPublic();
        return  ResponseEntity.ok(list);
    }
    @PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN')")
    @GetMapping("/testExam/{idExam}")
    public ResponseEntity<?> doExam(@PathVariable Integer idExam){
            List<question> list2 = questionRepository.getQuestionByExam(idExam);
            return ResponseEntity.ok(list2);
    }
}

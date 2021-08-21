package com.example.demo123.controller.user;

import com.example.demo123.dto.request.doExam;
import com.example.demo123.dto.response.*;
import com.example.demo123.entity.*;
import com.example.demo123.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user")
public class ExamController {
    @Autowired
    ExamRepository examRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    user_courseRepository user_courseRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserExamRepository userExamRepository;

    @GetMapping("/getExam/{id}/{userId}/{courseId}")
    public ResponseEntity<?> getExamById(@PathVariable long id,@PathVariable Long userId,@PathVariable Long courseId) {
        List<checkRegister> list = user_courseRepository.checkRegister(userId,courseId);
        if(list.size()!=0){
            Optional<examRespon> exam = examRepository.findExamDetail(id);
            return ResponseEntity.ok(exam);
        }else {
            return ResponseEntity.ok("faill");
        }

    }
    @GetMapping("examList/{userId}/{courseId}")
    public ResponseEntity<?> getExamListByCourse(@PathVariable Long userId,@PathVariable Long courseId){
        List<checkRegister> list = user_courseRepository.checkRegister(userId,courseId);
        if(list.size()!=0){
            List<Exam> examList = examRepository.getAllByCourse(courseId);
            return ResponseEntity.ok(examList);
        }else {
            return ResponseEntity.ok("faill");
        }
    }
    @GetMapping("/doExam/{idExam}/{userId}/{courseId}")
    public ResponseEntity<?> doExam(@PathVariable Integer idExam,@PathVariable Long userId,@PathVariable Long courseId){
        List<checkRegister> list = user_courseRepository.checkRegister(userId,courseId);
        if(list.size()!=0){
            List<question> list2 = questionRepository.getQuestionByExam(idExam);
            return ResponseEntity.ok(list2);
        }else {
            return ResponseEntity.ok("fail");
        }
    }
    @PostMapping("/saveResultTest")
    public ResponseEntity<?> saveResultTest(@RequestBody doExam doExams){

        int numberCorrect =0;
        int numberQuestion = doExams.getData().size();
        int numberWrong =0;
        for (int i=0;i<doExams.getData().size();i++){
            if(doExams.getData().get(i).getIsCorrect().toLowerCase().equals("true")){
                numberCorrect++;
            }else {
                numberWrong++;
            }
        }
        float score =(float) numberCorrect/numberQuestion*10;
        User user = userRepository.getById(doExams.getUserId());
        Exam exam = examRepository.getById(doExams.getIdExam());
        if(user==null || exam ==null){
            return ResponseEntity.ok("user or exam not found!");
        }else {
            UserExam userExam = new UserExam();
            userExam.setExam(exam);
            userExam.setUser(user);
            userExam.setStatus("active");
            userExam.setNumberCorrectAnswer(numberCorrect);
            userExam.setNumberWrongAnswer(numberWrong);
            userExam.setTime(doExams.getTime());
            userExam.setScore(score);
            userExamRepository.save(userExam);
        List<Answer> list = new ArrayList<>();
        for(int i=0;i<doExams.getData().size();i++){
            Answer answer = new Answer();
            answer.setExam_id(doExams.getIdExam());
            answer.setQuestion_id(doExams.getData().get(i).getId());
            answer.setAnswer(doExams.getData().get(i).getUserAnswer());
            answer.setUser_id(doExams.getUserId());
            answer.setTest_id(userExam.getId());
            answer.setCorrectAnswer(doExams.getData().get(i).getCorrectAnswer());
            answer.setIsCorrect(doExams.getData().get(i).getIsCorrect());
            list.add(answer);
        }
        answerRepository.saveAll(list);
            return ResponseEntity.ok("success");
        }

    }
    @PostMapping("saveAnswers")
    public ResponseEntity<?> saveAnswers(@RequestBody Answer answer){
        answerRepository.save(answer);
        return ResponseEntity.ok(answer);
    }
    @GetMapping("/listResultTest")
    public ResponseEntity<?> listResultTest(@RequestParam(value = "userId") Long userId,
                                            @RequestParam(value = "examId") Long examId){
        List<UserExam> list = userExamRepository.getResultListTest(examId,userId);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/ResultTestDetail")
    public ResponseEntity<?> ResultTestDetail(@RequestParam(value = "userId") Long userId,
                                              @RequestParam(value = "examId") Long examId,
                                              @RequestParam(value = "testId") Long testId){
        List<resultTestDetail> list = answerRepository.resultTestDetail(testId,userId,examId);
        List<UserExam> list2 = userExamRepository.getResultDetailTest(testId,examId,userId);
        objectResultDetailTest objectResultDetailTest = new objectResultDetailTest();
        objectResultDetailTest.setUserExam(list2);
        objectResultDetailTest.setResultTestDetail(list);
        return ResponseEntity.ok(objectResultDetailTest);
    }
}

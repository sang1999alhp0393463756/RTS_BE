package com.example.demo123.controller.user;

import com.example.demo123.dto.request.LessonRequest;
import com.example.demo123.dto.response.LessonResponse;
import com.example.demo123.dto.response.lessonOfStudent;
import com.example.demo123.dto.response.userRegisterCourse;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Lesson;
import com.example.demo123.repository.CourseRepository;
import com.example.demo123.repository.LessonRepository;
import com.example.demo123.repository.user_courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user/lesson")
public class LessonController {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    user_courseRepository user_courseRepository;

//    @GetMapping("/findAllLesson")
//    public ResponseEntity<?> getAllLesson() {
//        List<Lesson> lesson = lessonRepository.findAll();
//        List<LessonResponse> lessonList1 = new ArrayList<>();
//
//        for (int i = 0; i < lesson.size(); i++) {
//            LessonResponse lessonCustom = new LessonResponse();
//            lessonCustom.setContent(lesson.get(i).getContent());
//            lessonCustom.setTitle(lesson.get(i).getTitle());
//            lessonCustom.setImage(lesson.get(i).getImage());
//            lessonCustom.setLinkVideo(lesson.get(i).getLinkVideo());
//            lessonCustom.setCourseId(lesson.get(i).getCourse().getId());
//            lessonCustom.setCreatedDate(lesson.get(i).getCreatedDate());
//            lessonCustom.setCreatedBy(lesson.get(i).getCreatedBy());
//            lessonCustom.setModifiedBy(lesson.get(i).getModifiedBy());
//            lessonCustom.setModifiedDate(lesson.get(i).getModifiedDate());
//            lessonList1.add(lessonCustom);
//        }
//
//        return ResponseEntity.ok(lessonList1);
//    }

    @GetMapping("/getLessonByCourseIdForStudy/{idCourse}/{idUser}")
    public ResponseEntity<?> getLessonByIdCourse(@PathVariable long idCourse,@PathVariable long idUser) {
        List<userRegisterCourse> list = user_courseRepository.checkRegister(idUser,idCourse);
        if (list.size()==0){
            return ResponseEntity.ok("faill");
        }else {
            List<lessonOfStudent> lesson = lessonRepository.getLessonByCourseIdForStudy(idCourse);
            return ResponseEntity.ok(lesson);
        }


    }

    @GetMapping("/getLessonByIdForStudy/{idCourse}/{idUser}/{lessonId}")
    public ResponseEntity<?> getLessonById(@PathVariable long idCourse,@PathVariable long idUser,@PathVariable long lessonId) {
        List<userRegisterCourse> list = user_courseRepository.checkRegister(idUser,idCourse);
        if (list.size()==0){
            return ResponseEntity.ok("faill");
        }else {
            List<lessonOfStudent> lesson = lessonRepository.getLessonByIdForStudy(lessonId);
            return ResponseEntity.ok(lesson);
        }

    }
    @PostMapping("/successLesson/{idCourse}/{idUser}/{idLesson}")
    public ResponseEntity<?> successLesson(@PathVariable Long idCourse,@PathVariable Long idUser,@PathVariable Long idLesson){
        List<userRegisterCourse> list = user_courseRepository.checkRegister(idUser,idCourse);
        if (list.size()==0){
            return ResponseEntity.ok(list);
        }else {
           lessonRepository.updateStudy(idLesson,idUser);
            return ResponseEntity.ok("success");
        }
    }
}

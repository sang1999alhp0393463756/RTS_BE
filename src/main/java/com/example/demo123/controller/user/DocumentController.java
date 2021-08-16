package com.example.demo123.controller.user;

import com.example.demo123.dto.response.userRegisterCourse;
import com.example.demo123.entity.document;
import com.example.demo123.repository.DocumentRepository;
import com.example.demo123.repository.user_courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user/course")
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    user_courseRepository user_courseRepository;
    @GetMapping("getDocumentByCourseID")
    public ResponseEntity<?> getNameDocument(@RequestParam(name = "courseId") Long courseId,@RequestParam(name = "userId") Long userId){
        List<userRegisterCourse> list = user_courseRepository.checkRegister(userId,courseId);
        if(list.size()!=0){
            List<document> list2 = documentRepository.getAllByCourse(courseId);
            return ResponseEntity.ok(list2);
        }else {
            return ResponseEntity.ok("fail");
        }
    }
}

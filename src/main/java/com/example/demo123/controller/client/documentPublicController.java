package com.example.demo123.controller.client;

import com.example.demo123.dto.response.documentRespon;
import com.example.demo123.dto.response.userRegisterCourse;
import com.example.demo123.entity.document;
import com.example.demo123.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/client/document")
public class documentPublicController {
    @Autowired
    DocumentRepository documentRepository;
    @GetMapping("getDocumentByCourse")
    public ResponseEntity<?> getNameDocument(){
            List<documentRespon> list2 = documentRepository.getAllBySecurity();
            return ResponseEntity.ok(list2);
        }
    }


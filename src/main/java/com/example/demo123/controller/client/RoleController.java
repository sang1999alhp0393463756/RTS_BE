package com.example.demo123.controller.client;

import com.example.demo123.dto.request.CategoryRequest;
import com.example.demo123.dto.request.doExam;
import com.example.demo123.entity.Role;
import com.example.demo123.repository.RoleRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/showAllRole")
    public ResponseEntity<?> showAllRole() {
        List<Role> roleList = new ArrayList<>();
        roleList = roleRepository.findAll();
        return ResponseEntity.ok(roleList);
    }
}

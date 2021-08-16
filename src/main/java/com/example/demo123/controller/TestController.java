package com.example.demo123.controller;



import com.example.demo123.dto.response.JwtResponse;
import com.example.demo123.entity.ERole;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.security.jwt.JwtUtils;
import com.example.demo123.service.Ipml.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
    public String userAccess() {
        return "User Content.";
    }

//    @GetMapping("/all")
//    public ResponseEntity<?> homeAccess(HttpSession session){
//
//        String token =  session.getAttribute("token").toString();
//        Optional<User> user = userRepository.findByTokenEmail(token);
//        Set<Role> r = user.get().getRoles();
//        List<Role> role = r.stream().collect(Collectors.toList());
//        List<String> roles = new ArrayList<>();
//        for (int i=0;i<role.size();i++){
//            roles.add(role.get(0).getName().toString());
//        }
//
//        return ResponseEntity.ok(new JwtResponse(null,
//                user.get().getId(),
//                user.get().getUsername(),
//                user.get().getStatus(),
//                roles));
//    }

    @GetMapping("/expert")
    @PreAuthorize("hasRole('EXPERT')")
    public String expertAccess() {
        return "Expert Board.";
    }

    @GetMapping("/advisor")
    @PreAuthorize("hasRole('ADVISOR') or hasRole('ADMIN')")
    public String advisorAccess() {
        return "Advisor Board.";
    }

    @GetMapping("/marketer")
    @PreAuthorize("hasRole('MARKETER') or hasRole('ADMIN')")
    public String marketerAccess() {
        return "Marketer Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}
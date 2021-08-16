package com.example.demo123.controller.client;

import com.example.demo123.dto.response.userRespon;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class inforUserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/showUserById/{id}")
    public ResponseEntity<?> showAllById(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);
        userRespon infor = new userRespon();
        infor.setId(user.get().getId());
        infor.setUsername(user.get().getUsername());
        infor.setFullName(user.get().getFullName());
        infor.setPhoneNumber(user.get().getPhoneNumber());
        infor.setStatus(user.get().getStatus());
        Set<Role> r = user.get().getRoles();
        List<Role> role = r.stream().collect(Collectors.toList());
        List<String> roles = new ArrayList<>();
        for (int j=0;j<role.size();j++){
            roles.add(role.get(j).getName().toString());
        }
        infor.setRoles(roles);
        infor.setDescription(user.get().getDescription());
        infor.setAvatar(user.get().getAvatar());
        infor.setDob(user.get().getDob());
        return ResponseEntity.ok(infor);
    }
    @GetMapping("/getInfoExpertByCourse/{id}")
    public ResponseEntity<User> getExpertByCourse(@PathVariable long id) {
        Optional<User> userOptional = userRepository.getInfoExpertByCourse(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

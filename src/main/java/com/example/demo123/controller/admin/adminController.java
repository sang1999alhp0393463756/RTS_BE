package com.example.demo123.controller.admin;

import com.example.demo123.dto.request.PaymentRequest;
import com.example.demo123.dto.request.user_rolesRequest;
import com.example.demo123.dto.response.PaymentRespon;
import com.example.demo123.dto.response.userRespon;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.entity.payment;
import com.example.demo123.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private QueryRepository QueryRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/showUser")
    public ResponseEntity<?> showAll() {
        List<User> list = userRepository.getAllUserWithoutAdmin();
        List<userRespon> user = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            userRespon infor = new userRespon();
            infor.setId(list.get(i).getId());
            infor.setUsername(list.get(i).getUsername());
            infor.setFullName(list.get(i).getFullName());
            infor.setPhoneNumber(list.get(i).getPhoneNumber());
            infor.setStatus(list.get(i).getStatus());
            Set<Role> r = list.get(i).getRoles();
            List<Role> role = r.stream().collect(Collectors.toList());
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < role.size(); j++) {
                roles.add(role.get(j).getName().toString());
            }
            infor.setRoles(roles);
            infor.setDescription(list.get(i).getDescription());
            user.add(infor);
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/showUserById/{id}")
    public ResponseEntity<?> showAllById(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.get() == null || !user.get().getStatus().equals("active")) {
            return ResponseEntity.ok("user not empty!");
        } else {
            userRespon infor = new userRespon();
            infor.setId(user.get().getId());
            infor.setUsername(user.get().getUsername());
            infor.setFullName(user.get().getFullName());
            infor.setPhoneNumber(user.get().getPhoneNumber());
            infor.setStatus(user.get().getStatus());
            Set<Role> r = user.get().getRoles();
            List<Role> role = r.stream().collect(Collectors.toList());
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < role.size(); j++) {
                roles.add(role.get(j).getName().toString());
            }
            infor.setRoles(roles);
            infor.setDescription(user.get().getDescription());

            return ResponseEntity.ok(infor);
        }

    }

//    @PutMapping("/updateUser/{id}")
//    public ResponseEntity<User> updateUserById(@PathVariable("id") long id, @Valid @RequestBody userRespon user) {
//        Optional<User> userOptional = userRepository.findById(id);
//        Set<Role> r = userOptional.get().getRoles();
//
//        if (userOptional.isPresent()) {
//            User user1 = userOptional.get();
//            user1.setUsername(user.getUsername());
//            user1.setFullName(user.getFullName());
//            user1.setPhoneNumber(user.getPhoneNumber());
//            user1.setRoles(r);
//
//            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    @PutMapping("/deleteCourse/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        Course courseOptional = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Course Not Found with id: " + id));
        if (courseOptional != null) {
            try {
                courseOptional.setStatus("pending");
                courseRepository.save(courseOptional);
                return ResponseEntity.ok(courseOptional);
            } catch (NullPointerException e) {
                return ResponseEntity.ok(e.toString());
            }

        } else {
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/getRole")
    public List<Role> getRole() {
        List<Role> getRole = new ArrayList<>();
        getRole = roleRepository.getRole();
        return getRole;
    }

    @PutMapping("/updateRole")
    public String update(@Valid @RequestBody user_rolesRequest user_rolesRequest) {
        String status = "";
        try {
            Integer role = Integer.parseInt(user_rolesRequest.getId_role());
            Integer user = Integer.parseInt(user_rolesRequest.getId_user());
            QueryRepository.updateRoleForUser(role, user);
            status = "update success";
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    @PutMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        User userOptional = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        ;
        if (userOptional != null) {
            try {
                userOptional.setStatus("pending");
                userRepository.save(userOptional);
                return ResponseEntity.ok(userOptional);
            } catch (NullPointerException e) {
                return ResponseEntity.ok(e.toString());
            }
        } else {
            return ResponseEntity.ok("fail");
        }
    }

//payment cũng cần 1 màn hình lịch sử thanh toán giống hệt bên expert nhưng trong
// màn hình này thì có thêm chức năng update tiền và active request

    //api này dùng cho chức năng sau khi đã chuyển tiền xong cho expert
    //admin nhập số tiền mà expert đã rút vào
    //gửi về cùng với id của course
    @PutMapping("updateMoneyForCourse/{withdrawn_money}/{course_id}")
    public ResponseEntity<?> updateMoneyForCourse(@PathVariable float withdrawn_money,@PathVariable Long course_id){
        Course course = courseRepository.getById(course_id);
        float tien_rut = withdrawn_money+course.getWithdrawn_money();
        float tien_du = course.getTotal_money()-tien_rut;
        course.setRemaining_amount(tien_du);
        course.setWithdrawn_money(tien_rut);
        courseRepository.save(course);
        return ResponseEntity.ok("success");
    }
    //update cho cái lượt request đó để chuyển từ bên lịch sử yêu cầu sang lịch sử đã thanh toán
    @PutMapping("updateStatusPayment")
    public ResponseEntity<?> updateStatusPayment(@RequestParam(name = "id") Long id,@RequestParam(name = "status") String status){
        payment payment1 = paymentRepository.getById(id);
        if(payment1!=null){
            payment1.setStatus(status);
            paymentRepository.save(payment1);
            return ResponseEntity.ok(payment1);
        }else {
            return ResponseEntity.ok("fail");
        }
    }
}

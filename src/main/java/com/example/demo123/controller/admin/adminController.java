package com.example.demo123.controller.admin;

import com.example.demo123.dto.request.PaymentRequest;
import com.example.demo123.dto.request.activePaymentRequest;
import com.example.demo123.dto.request.user_rolesRequest;
import com.example.demo123.dto.response.PaymentRespon;
import com.example.demo123.dto.response.respon;
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
        if (user.get() == null) {
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

    @PutMapping("/updateStatusUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id,@RequestParam(name = "status") String status) {
        User userOptional = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        ;
        if (userOptional != null) {
            try {
                userOptional.setStatus(status);
                userRepository.save(userOptional);
                return ResponseEntity.ok(userOptional);
            } catch (NullPointerException e) {
                return ResponseEntity.ok(e.toString());
            }
        } else {
            return ResponseEntity.ok("fail");
        }
    }

//payment cu??ng c????n 1 ma??n hi??nh li??ch s???? thanh toa??n gi????ng h????t b??n expert nh??ng trong
// ma??n hi??nh na??y thi?? co?? th??m ch????c n??ng update ti????n va?? active request

    //api na??y du??ng cho ch????c n??ng sau khi ??a?? chuy????n ti????n xong cho expert
    //admin nh????p s???? ti????n ma?? expert ??a?? ru??t va??o
    //g????i v???? cu??ng v????i id cu??a course
    //update cho ca??i l??????t request ??o?? ?????? chuy????n t???? b??n li??ch s???? y??u c????u sang li??ch s???? ??a?? thanh toa??n
    @PutMapping("/updateStatusPayment")
    public ResponseEntity<?> updateStatusPayment(@RequestBody activePaymentRequest activePaymentRequest){
        Course course = courseRepository.findById(activePaymentRequest.getCourse_id()).orElseThrow(() -> new UsernameNotFoundException("course Not Found"));

        if(course!=null&& course.getRemaining_amount()>=activePaymentRequest.getWithdrawn_money()){
            payment payment1 = paymentRepository.getById(activePaymentRequest.getPaymentId());
            float tien_rut = activePaymentRequest.getWithdrawn_money()+course.getWithdrawn_money();
            float tien_du = course.getTotal_money()-tien_rut;
            course.setRemaining_amount(tien_du);
            course.setWithdrawn_money(tien_rut);
            payment1.setStatus("active");
            courseRepository.save(course);
            paymentRepository.save(payment1);
            return ResponseEntity.ok(new respon("true","thanh toa??n tha??nh c??ng"));
        }else {
            String status1 = "thanh toa??n kh??ng tha??nh c??ng, s???? ti????n co??n la??i trong kho??a ho??c kh??ng ??u?? ?????? th????c hi????n y??u c????u!";
            String status2="s???? ti????n co??n la??i trong kho??a ho??c cu??a ba??n la??: "+course.getRemaining_amount();
            return ResponseEntity.ok(new respon("false",status1+"\n"+status2));
        }
    }
    @GetMapping("/getRequestPayment")
    public ResponseEntity<?> getRequestPayment(@RequestParam(name = "status") String status){
        List<PaymentRespon> list = paymentRepository.getAllByStatus(status);
        return ResponseEntity.ok(list);
    }
}

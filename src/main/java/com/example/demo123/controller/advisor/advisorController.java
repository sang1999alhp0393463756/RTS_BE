package com.example.demo123.controller.advisor;

import com.example.demo123.dto.request.updateCourseUser;
import com.example.demo123.dto.response.*;
import com.example.demo123.entity.Category;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADVISOR') or hasRole('ADMIN')")
@RequestMapping("/advisor")
public class advisorController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private user_courseRepository user_courseRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JavaMailSender sender;
    @GetMapping("/allRegistered")
    public ResponseEntity<?> getRegisteredList() {
        List<User> list = userRepository.getRegisteredList();
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
    @GetMapping("/allRegisteredByStatus")
    public ResponseEntity<?> getRegisteredList(@RequestParam(name = "status") String status) {
        List<user_courseRespon> list = userRepository.getRegisteredListByStatus(status);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getRegisteredById/{id}")
    public ResponseEntity<?> getRegisteredById(@PathVariable long id) {
        Optional<User> user = userRepository.getRegisteredListById(id);
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
    @GetMapping("/listCourseByStatus")
    public ResponseEntity<?> listCourseByStatus(@RequestParam(name = "status") String status) {
        List<Course> list = new ArrayList<>();
        list = courseRepository.courseByStatus(status);
        List<course> coursesList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            course course = new course();
            User user = userRepository.getById(list.get(i).getCoreExpert());
            userRespon infor = new userRespon();
            infor.setId(user.getId());
            infor.setUsername(user.getUsername());
            infor.setFullName(user.getFullName());
            infor.setPhoneNumber(user.getPhoneNumber());
            infor.setStatus(user.getStatus());
            Set<Role> r = user.getRoles();
            List<Role> role = r.stream().collect(Collectors.toList());
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < role.size(); j++) {
                roles.add(role.get(j).getName().toString());
            }
            infor.setRoles(roles);
            infor.setDescription(user.getDescription());
            course.setExpert(infor);
            course.setId(list.get(i).getId());
            course.setContent(list.get(i).getContent());
            course.setThumbnail(list.get(i).getThumbnail());
            course.setTitle(list.get(i).getTitle());
            course.setSortDescription(list.get(i).getSortDescription());
            course.setCoreExpert(list.get(i).getCoreExpert());
            course.setPrice(list.get(i).getPrice());
            course.setRatingToltal(list.get(i).getRatingToltal());
            course.setCategoryId(list.get(i).getCategory().getId());
            course.setCategoryName(list.get(i).getCategory().getName());
            course.setCreateDate(list.get(i).getCreatedDate());
            course.setStatus(list.get(i).getStatus());
            course.setSale(list.get(i).getSale());
            coursesList.add(course);
        }
        return ResponseEntity.ok(coursesList);
    }
    @GetMapping("/showUserOfCourse/{id}")
    public ResponseEntity<?> showUserOfCourse(@PathVariable long id) {
        List<User> userList = userRepository.userOfCourse(id);
        List<userRespon> userResponsList = new ArrayList<>();
        for(int i = 0; i < userList.size(); i++){
            userRespon userRespon = new userRespon();
            userRespon.setId(userList.get(i).getId());
            userRespon.setUsername(userList.get(i).getUsername());
            userRespon.setFullName(userList.get(i).getFullName());
            userRespon.setPhoneNumber(userList.get(i).getPhoneNumber());
            userRespon.setStatus(userList.get(i).getStatus());
            Set<Role> r = userList.get(i).getRoles();
            List<Role> role = r.stream().collect(Collectors.toList());
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < role.size(); j++) {
                roles.add(role.get(j).getName().toString());
            }
            userRespon.setRoles(roles);
            userRespon.setDescription(userList.get(i).getDescription());
            userResponsList.add(userRespon);
        }
        return ResponseEntity.ok(userResponsList);
    }
    @PutMapping("activeCourse/{status}")
    public ResponseEntity<?> activeCourse(@PathVariable String status,@RequestParam(name = "userID") Long userID,@RequestParam(name = "courseID") Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new UsernameNotFoundException("course Not Found w"));
        User user = userRepository.findById(userID) .orElseThrow(() -> new UsernameNotFoundException("User Not Found w"));
            course.setStatus(status);
            course.setDate_duyet(new Date());
            course.setEmail_duyet(user.getUsername());
            courseRepository.save(course);
            return ResponseEntity.ok(course);
    }
    @GetMapping("/getUserByStatus")
    public ResponseEntity<?> getUserByStatus(@RequestParam(name = "status") String status,@RequestParam(name = "course_id") Long course_id){
        List<userRegisterCourse> user_courses = user_courseRepository.getByStatus(status,course_id);
        List<userRespon> list = new ArrayList<>();
        for (int i=0;i<user_courses.size();i++){
            User user = userRepository.getById(user_courses.get(i).getUser_id());
            userRespon userRespon = new userRespon();
            userRespon.setId(user.getId());
            userRespon.setUsername(user.getUsername());
            userRespon.setFullName(user.getFullName());
            userRespon.setPhoneNumber(user.getPhoneNumber());
            userRespon.setStatus(user.getStatus());
            Set<Role> r = user.getRoles();
            List<Role> role = r.stream().collect(Collectors.toList());
            List<String> roles = new ArrayList<>();
            for (int j = 0; j < role.size(); j++) {
                roles.add(role.get(j).getName().toString());
            }
            userRespon.setRoles(roles);
            userRespon.setDescription(user.getDescription());
            list.add(userRespon);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getListRegisterCourseForAdvisor")
    public ResponseEntity<?> getListRegisterCourseForAdvisor(){
        List<AdvisorListRegister> advisorListRegisterList = new ArrayList<>();
        List<userRegisterCourse> userRegisterCourses = user_courseRepository.getListRegisterAdvisor();
        for(int i = 0; i < userRegisterCourses.size(); i++ ){
            AdvisorListRegister advisorListRegister = new AdvisorListRegister();
            Category category = categoryRepository.findById(userRegisterCourses.get(i).getCategory_id()).orElseThrow(() -> new RuntimeException("Error: category is not found."));
            advisorListRegister.setFullName(userRegisterCourses.get(i).getFullName());
            advisorListRegister.setUsername(userRegisterCourses.get(i).getUsername());
            advisorListRegister.setPhoneNumber(userRegisterCourses.get(i).getPhone_number());
            advisorListRegister.setDate(userRegisterCourses.get(i).getDate());
            advisorListRegister.setStatus(userRegisterCourses.get(i).getStatus());
            advisorListRegister.setPrice(userRegisterCourses.get(i).getPrice());
            advisorListRegister.setTitle(userRegisterCourses.get(i).getTitle());
            advisorListRegister.setCategory(category);
            advisorListRegister.setNguoi_duyet(userRegisterCourses.get(i).getNguoi_duyet());
            advisorListRegister.setUser_id(userRegisterCourses.get(i).getUser_id());
            advisorListRegister.setCourse_id(userRegisterCourses.get(i).getCourse_id());
            advisorListRegisterList.add(advisorListRegister);
        }
        return ResponseEntity.ok(advisorListRegisterList);
    }
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(updateCourseUser updateCourseUser){


        List<checkRegister> user_courses = user_courseRepository.listRegister(updateCourseUser.getCourseID());
            Course course = courseRepository.getById(updateCourseUser.getCourseID());
            User user = userRepository.getById(updateCourseUser.getUserID());
            float price =updateCourseUser.getPrice();

            float total_amount = 0;
            for (int i=0;i<user_courses.size();i++){
                total_amount+=user_courses.get(i).getTien_nop();
            }
            float remaining_amount = total_amount- course.getWithdrawn_money();
            course.setTotal_money(total_amount);
            course.setRemaining_amount(remaining_amount);
            user_courseRepository.updateStauts(updateCourseUser.getNguoi_duyet(),price,updateCourseUser.getUserID(),updateCourseUser.getCourseID());
            courseRepository.save(course);
            //send mail verify
            Date date = new Date();


            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            try {
                helper.setTo(user.getUsername());
                String hello= "<h1 style=\"color:blue;\">Xin chào "+user.getFullName()+"</h1>";
                String content="<d>"+hello+"<p>cảm ơn bạn đã tới với website của chúng tôi và mua khóa học <b>"+course.getTitle()+"</b> với giá <b>"+updateCourseUser.getPrice()+" vnđ </b>" +
                        "<br>Bạn có thể học khóa học này từ ngày :"+date.toString()+"</p></d>";
                helper.setText(content,true);
                helper.setSubject("Mail From RTS_Learning_Solution");
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.ok("Error while sending mail ..");
            }
            sender.send(message);
            return ResponseEntity.ok("success");
//        }catch (Exception e){
//            return ResponseEntity.ok(e.toString());
//        }
    }
    @DeleteMapping("/userOutCourse")
    public ResponseEntity<?> userOutCourse(@RequestParam(name = "user_id") Long user_id,@RequestParam(name = "course_id") Long course_id){
        try {
            user_courseRepository.detele(user_id,course_id);
            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.ok(e.toString());
        }
    }
}

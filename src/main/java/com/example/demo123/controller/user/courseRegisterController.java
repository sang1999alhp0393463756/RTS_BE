package com.example.demo123.controller.user;

import com.example.demo123.dto.request.course_user;
import com.example.demo123.dto.response.*;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.User;
import com.example.demo123.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user/course")
public class courseRegisterController {
    @Autowired
    private QueryRepository queryRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private user_courseRepository user_courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender sender;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody course_user course_user){
        String status = "";
        Date date= new Date();
        try {
            User user = userRepository.getById(course_user.getUserID());
            Course course = courseRepository.getById(course_user.getCourseID());
            if(user==null || user.getStatus().equals("pending")){
                status = "tài khoản chưa đủ điều kiện để đăng kí khóa học, hãy kiểm tra tài khoản xem đã kích hoạt chưa";
            }else {
                queryRepository.register(course_user.getUserID(),course_user.getCourseID(),date);
                status= "1";

                //send mail verify
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                try {
                    helper.setTo(user.getUsername());
                    String hello= "<h1 style=\"color:blue;\">Xin chào "+user.getFullName()+"</h1>";
                    String content="<d>"+hello+"<p>cảm ơn bạn đã tới với website của chúng tôi và mua khóa học <b>"+course.getTitle()+"</b> với giá <b>"+course.getPrice()+" vnđ </b>" +
                            "vui lòng thanh toán với chúng tôi theo <br> số tài khoản : 01234569875225 <br>ngân hàng :Vietcombank<br> cú pháp : " +
                            ""+user.getFullName()+" "+user.getPhoneNumber()+" "+user.getId()+""+course.getId()+"</p></d>";

                    helper.setText(content,true);
                    helper.setSubject("Mail From RTS_Learning_Solution");
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return ResponseEntity.ok("Error while sending mail ..");
                }
                sender.send(message);
            }

        }catch (Exception e){
            status= e.toString();
        }

        return ResponseEntity.ok(status);
    }

    @GetMapping("/showCourseOfUser/{id}")
    public ResponseEntity<?> showCourseOfUser(@PathVariable long id) {
        List<courseRegistiteried> courseList = courseRepository.courseOfUser(id);
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/checkRegister/{idUser}/{idCourse}")
    public ResponseEntity<?> checkRegister(@PathVariable Long idUser,@PathVariable Long idCourse){
        List<userRegisterCourse> userRegisterCourse = user_courseRepository.checkRegister2(idUser,idCourse);
        return ResponseEntity.ok(userRegisterCourse);
    }
    @GetMapping("listStudentsOfCourse/{course_id}")
    public ResponseEntity<?>listStudentsOfCourse(@PathVariable long course_id){
        List<StudentsOfCourse> list = user_courseRepository.getStudentsOfcourse(course_id);
        return ResponseEntity.ok(list);
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

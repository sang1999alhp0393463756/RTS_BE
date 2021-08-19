package com.example.demo123.controller.user;

import com.example.demo123.dto.request.BlogThumbnail;
import com.example.demo123.dto.request.blogRequest;
import com.example.demo123.dto.request.course_user;
import com.example.demo123.dto.response.*;
import com.example.demo123.entity.Blog;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.User;
import com.example.demo123.repository.*;
import com.example.demo123.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @CrossOrigin
    @RestController
    @PreAuthorize("hasRole('ADMIN') or hasRole('MARKETER')")
    @RequestMapping("/marketer/blog")
    public static class YourBlogController {

        @Autowired
        private BlogRepository blogRepository;
        @Autowired
        private UserRepository userRepository;
        private AmazonClient amazonClient;
        @Autowired
        YourBlogController(AmazonClient amazonClient) {
            this.amazonClient = amazonClient;
        }

        @PostMapping("/saveImageContent")
        public ResponseEntity<?> saveThumbnailBlog(@ModelAttribute BlogThumbnail imageBlog) {
            return ResponseEntity.ok(this.amazonClient.uploadFile(imageBlog.getImageBlog()));
        }
        @PostMapping("/addBlog")
        public ResponseEntity<?> addBlog(@ModelAttribute blogRequest blog) {
            try {
                User user = userRepository.getById(blog.getUser_id());
                if (user == null) {
                    System.out.printf("" + user.getFullName());
                    return ResponseEntity.ok("fail");
                } else {
                    Blog blog1 = new Blog();
                    blog1.setContent(blog.getContent());
                    blog1.setThumbnail(this.amazonClient.uploadFile(blog.getThumbnail()));
                    blog1.setTitle(blog.getTitle());
                    blog1.setSortDescription(blog.getSortDescription());
                    blog1.setUser(user);
                    blog1.setStatus("pending");
                    blogRepository.save(blog1);
                    return ResponseEntity.ok("success");
                }
            } catch (Exception e) {
                return ResponseEntity.ok("" + e.toString());
            }
        }
    //    @GetMapping("uploadImageBlog/{id}")
    //    public ResponseEntity<?> getImageById(@PathVariable long id,@RequestParam(name = "image") String image) {
    //        try {
    //            Path imageFile = Paths.get("upload-dir/Blog", image);
    //            byte[] imageBuffer = Files.readAllBytes(imageFile);
    //            ByteArrayResource byteArrayResource = new ByteArrayResource(imageBuffer);
    //
    //            return ResponseEntity.ok()
    //                    .contentType(MediaType.parseMediaType("image/png"))
    //                    .body(byteArrayResource);
    //        } catch (Exception e) {
    //
    //        }
    //        return ResponseEntity.badRequest().build();
    //    }
        @PutMapping("/updateBlog/{id}")
        public ResponseEntity<?> updateBlog(@PathVariable Long id,@ModelAttribute blogRequest blog) {
            try {
                Blog blog1 = blogRepository.findBlogById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with blog"));
                blog1.setTitle(blog.getTitle());
                blog1.setThumbnail(this.amazonClient.uploadFile(blog.getThumbnail()));
                blog1.setContent(blog.getContent());
                blog1.setSortDescription(blog.getSortDescription());
                blogRepository.save(blog1);
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                return ResponseEntity.ok(e.toString());
            }
        }

        @PutMapping("/deleteBlog")
        public ResponseEntity<?> deleteBlog(@RequestParam(name = "id") Long id) {
            try {
                Blog blog = blogRepository.findBlogById(id).orElseThrow(() -> new UsernameNotFoundException("Blog Not Found "));
                blog.setStatus("pending");
                blogRepository.save(blog);
                return ResponseEntity.ok(blog);
            } catch (Exception e) {
                return ResponseEntity.ok(e.toString());
            }
        }

        @GetMapping("/myBlogList")
        public ResponseEntity<?> myBlogList(@RequestParam(name = "user_id") Long user_id) {
            List<Blog> list = blogRepository.findBlogByUserID(user_id);
            List<BlogRespon> listRequest = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                userRespon userRespon = new userRespon();
                userRespon.setId(list.get(i).getUser().getId());
                userRespon.setFullName(list.get(i).getUser().getFullName());
                BlogRespon blog2 = new BlogRespon(list.get(i).getId(),
                        list.get(i).getCreatedBy(),
                        list.get(i).getCreatedDate(),
                        list.get(i).getModifiedBy(),
                        list.get(i).getModifiedDate(),
                        userRespon,
                        list.get(i).getThumbnail(),
                        list.get(i).getTitle(),
                        list.get(i).getContent(),
                        list.get(i).getSortDescription(),
                        list.get(i).getStatus());
                listRequest.add(blog2);
            }

            return ResponseEntity.ok(listRequest);
        }
    }
}

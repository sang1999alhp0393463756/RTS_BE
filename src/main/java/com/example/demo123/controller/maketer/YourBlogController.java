package com.example.demo123.controller.maketer;

import com.example.demo123.dto.request.BlogThumbnail;
import com.example.demo123.dto.request.blogRequest;
import com.example.demo123.dto.request.updateBlogRequest;
import com.example.demo123.dto.response.BlogRespon;
import com.example.demo123.dto.response.userRespon;
import com.example.demo123.entity.Blog;
import com.example.demo123.entity.User;
import com.example.demo123.repository.BlogRepository;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user/blog")
public class YourBlogController {

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
                if(user.getRoles().equals("ROLE_ADMIN")){
                    blog1.setStatus("active");
                }else {
                    blog1.setStatus("pending");
                }
                blog1.setContent(blog.getContent());
                blog1.setThumbnail(this.amazonClient.uploadFile(blog.getThumbnail()));
                blog1.setTitle(blog.getTitle());
                blog1.setSortDescription(blog.getSortDescription());
                blog1.setUser(user);

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
    public ResponseEntity<?> updateBlog(@PathVariable Long id,@ModelAttribute updateBlogRequest blog) {
        try {
            Blog blog1 = blogRepository.findBlogById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with blog"));
            blog1.setTitle(blog.getTitle());
            if (!blog.getThumbnail().equals("null")&&!blog.getThumbnail().equals("")){
                blog1.setThumbnail(blog.getThumbnail());
            }else {
                blog1.setThumbnail(blog1.getThumbnail());
            }

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

package com.example.demo123.controller.client;

import com.example.demo123.dto.response.BlogRespon;

import com.example.demo123.dto.response.userRespon;
import com.example.demo123.entity.Blog;
import com.example.demo123.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client/blog")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;
    @GetMapping("/top4Blog")
    public ResponseEntity<?> top4Blog(){
        List<Blog> list = blogRepository.getTop4();
        List<BlogRespon> top4 = new ArrayList<>();
        for (int i=0;i<list.size();i++){
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
            top4.add(blog2);
        }
        return ResponseEntity.ok(top4);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAll(){
        List<Blog> list= blogRepository.findAllActive();
        List<BlogRespon> listRequest = new ArrayList<>();
        for (int i=0;i<list.size();i++){
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

    @GetMapping("/blogDetail")
    public ResponseEntity<?> getBlogById(@RequestParam(name = "id") Long id){
        Blog blog = blogRepository.findBlogByIdActive(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with blog"));
        userRespon userRespon = new userRespon();
        userRespon.setId(blog.getUser().getId());
        userRespon.setFullName(blog.getUser().getFullName());
        BlogRespon blog2 = new BlogRespon(blog.getId(),
                blog.getCreatedBy(),
                blog.getCreatedDate(),
                blog.getModifiedBy(),
                blog.getModifiedDate(),
                userRespon,
                blog.getThumbnail(),
                blog.getTitle(),
                blog.getContent(),
                blog.getSortDescription(),
                blog.getStatus());
        return ResponseEntity.ok(blog);
    }


}

package com.example.demo123.controller.maketer;

import com.example.demo123.dto.request.SliderUpdateRequest;
import com.example.demo123.dto.request.sliderRequest;
import com.example.demo123.dto.response.*;
import com.example.demo123.entity.*;
import com.example.demo123.repository.*;
import com.example.demo123.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('MARKETER') or hasRole('ADMIN')")
@RequestMapping("/marketer")
public class MarketerController {
    @Autowired
    SliderRepository sliderRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CategoryRequestRepository categoryRequestRepository;
    private AmazonClient amazonClient;
    @Autowired
    MarketerController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    // Slider CRUD
    @GetMapping("/showSliders/{id}")
    public ResponseEntity<Slider> getSliderById(@PathVariable long id) {
        Optional<Slider> sliderOptional = sliderRepository.findById(id);
        return sliderOptional.map(slider -> new ResponseEntity<>(slider, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addSliders")
    public ResponseEntity<Slider> createNewSlider(@ModelAttribute sliderRequest slider) {
        try {
            Slider slider1 = sliderRepository.save(new Slider(slider.getTitle(),
                    this.amazonClient.uploadFile(slider.getImage()),
                    slider.getLink(),
                    slider.getStatus()));
            return new ResponseEntity<>(slider1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateSlider/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable long id,
                                          @ModelAttribute SliderUpdateRequest slider) {
        Slider slider1 = sliderRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Slider Not Found with lesson"));
        slider1.setTitle(slider.getTitle());
        slider1.setImage(slider.getImage());
        slider1.setLink(slider.getLink());
        slider1.setStatus(slider.getStatus());
        sliderRepository.save(slider1);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/showSlider")
    public ResponseEntity<?> showAllSlider() {
        List<Slider> sliderList = sliderRepository.findAll();
        return ResponseEntity.ok(sliderList);
    }


    @PutMapping("/updateStatusSlider/{id}")
    public ResponseEntity<?> updateStatusSlider(@PathVariable long id,@RequestParam(name = "status") String status) {
        try {
            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Slider Not Found "));
            slider.setStatus(status);
            sliderRepository.save(slider);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }


    @PutMapping("/deleteSliders/{id}")
    public ResponseEntity<?> deleteSliders(@PathVariable long id) {
        try {
            Slider slider = sliderRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Slider Not Found "));
            sliderRepository.delete(slider);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @GetMapping("/listBlogMarketer")
    public ResponseEntity<?> getAll(){
        List<Blog> list= blogRepository.findAll();
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
    @GetMapping("/blogDetailMarketer")

    public ResponseEntity<?> getBlogById(@RequestParam(name = "id") Long id){
        Blog blog = blogRepository.findBlogById(id)
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
    @PutMapping("updateStatusBlog/{id}")
    public ResponseEntity<?> activeBlog(@PathVariable long id,@RequestParam(name = "status") String status){
        Blog blog = blogRepository.findBlogById(id).orElseThrow(() -> new UsernameNotFoundException("Blog Not Found "));
        if (blog!=null){
            blog.setStatus(status);
            blogRepository.save(blog);
            return ResponseEntity.ok("success");
        }else {
            return ResponseEntity.ok("fail");
        }
    }

    @PutMapping("/deleteBlog")
    public ResponseEntity<?> deleteBlog(@RequestParam(name = "id") Long id) {
        try {
            Blog blog = blogRepository.findBlogById(id).orElseThrow(() -> new UsernameNotFoundException("Blog Not Found "));
            if(blog.getStatus().equals("pending")){
                blogRepository.delete(blog);
                return ResponseEntity.ok(blog);
            }else {
                return ResponseEntity.ok("blog active, can't delete");
            }

        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> createNewCategory(@Valid @RequestBody CategoryResponse categoryResponse) {
        List<Course> course = courseRepository.findCourseById(categoryResponse.getCourseId());
        Category category = new Category();
        category.setName(categoryResponse.getName());
        category.setDescription(categoryResponse.getDescription());
        category.setCourses(course);
        category.setStatus("pending");
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id,
                                            @Valid @RequestBody CategoryResponse category) {
        Category category1 = categoryRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with category"));
        List<Course> courses = courseRepository.findCourseById(category.getCourseId());
        category1.setName(category.getName());
        category1.setDescription(category.getDescription());
        category1.setCourses(courses);
        categoryRepository.save(category1);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/updateStatusCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id,@RequestParam(name = "status") String status) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Category Not Found "));
            category.setStatus(status);
            categoryRepository.save(category);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }



}

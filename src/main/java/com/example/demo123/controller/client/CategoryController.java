package com.example.demo123.controller.client;

import com.example.demo123.dto.request.CategoryRequest;
import com.example.demo123.dto.response.CategoryResponse;
import com.example.demo123.entity.Category;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Lesson;
import com.example.demo123.repository.CategoryRepository;
import com.example.demo123.repository.CategoryRequestRepository;
import com.example.demo123.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/client/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRequestRepository categoryRequestRepository;

    @GetMapping("/showAll")
    public ResponseEntity<?> showAllCategory() {
        List<CategoryRequest> categoryList = new ArrayList<>();
        categoryList = categoryRequestRepository.showAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/showCategory/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/showCategoryByIT")
    public List<Category> showCategoryByIT() {
        List<Category> categoryList = new ArrayList<>();
        categoryList = categoryRepository.showCategoryByIT();
        return categoryList;
    }
    @GetMapping("/countCourseInCagegory/{id}")
    public ResponseEntity<?> countCourseInCagegory(@PathVariable Long id){
        List<Course> count = courseRepository.countCourseInCagegory(id);
        return ResponseEntity.ok(count.size());
    }
}

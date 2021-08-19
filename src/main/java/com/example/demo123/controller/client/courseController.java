package com.example.demo123.controller.client;
import com.example.demo123.dto.response.course;
import com.example.demo123.dto.response.lessonOfStudent;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Lesson;
import com.example.demo123.repository.CourseRepository;
import com.example.demo123.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/client/course")
public class courseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    LessonRepository lessonRepository;

    @GetMapping("/allCourse")
    public ResponseEntity<?> showAll() {
        List<Course> list = new ArrayList<>();
        list = courseRepository.findAll();
        List<course> coursesList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            course course = new course();
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

    @GetMapping("/countCourse")

    public List<Course> showCourseByPaging(@RequestHeader(defaultValue = "1") int page) {
        int pageSize = 2;
        List<Course> listPerPage = courseRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
        return listPerPage;
    }


    @GetMapping("/findCourse/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable long id) {
       Course courseOptional = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("course Not Found with id: " +id));
        if (courseOptional!=null){
            course course = new course();
            course.setId(courseOptional.getId());
            course.setContent(courseOptional.getContent());
            course.setThumbnail(courseOptional.getThumbnail());
            course.setTitle(courseOptional.getTitle());
            course.setSortDescription(courseOptional.getSortDescription());
            course.setCoreExpert(courseOptional.getCoreExpert());
            course.setPrice(courseOptional.getPrice());
            course.setRatingToltal(courseOptional.getRatingToltal());
            course.setCategoryId(courseOptional.getCategory().getId());
            course.setCategoryName(courseOptional.getCategory().getName());
            course.setCreateDate(courseOptional.getCreatedDate());
            course.setStatus(courseOptional.getStatus());
            course.setSale(courseOptional.getSale());
            return ResponseEntity.ok(course);
        }else {
            return ResponseEntity.ok("null");
        }
    }

    @GetMapping("/top4")
    public List<Course> top4() {
        List<Course> list = new ArrayList<>();
        list = courseRepository.top4();
        return list;
    }

    @GetMapping("/top5")
    public ResponseEntity<?> getTop5() {
        List<Course> list = courseRepository.findTop5();
        List<Course> top5 = new ArrayList<>();
        if (list.size() >= 4) {
            for (int i = 0; i < 4; i++) {

                top5.add(list.get(i));
            }
        } else {
            top5 = list;
        }
        return ResponseEntity.ok(top5);
    }

    @GetMapping("/top5Vote")
    public ResponseEntity<?> getTop5Vote() {
        List<Course> list = courseRepository.allCourseHaveVote();
        List<Course> top5 = new ArrayList<>();
        if (list.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                top5.add(list.get(i));
            }
        } else {
            top5 = list;
        }
        return ResponseEntity.ok(top5);
    }
    @GetMapping("/getTitleLessonByCourse/{idCourse}")
    public ResponseEntity<?> getLessonByIdCourse(@PathVariable long idCourse) {
        List<lessonOfStudent> lesson = lessonRepository.getLessonByCourseIdForStudy(idCourse);
        List<Lesson> titleLesson = new ArrayList<>();
        for (int i=0;i<lesson.size();i++){
        Lesson lesson1 = new Lesson();
        lesson1.setStatus(lesson.get(i).getStatus());
        lesson1.setId(lesson.get(i).getId());
        lesson1.setTitle(lesson.get(i).getTitle());
        titleLesson.add(lesson1);
        }
        return ResponseEntity.ok(titleLesson);
    }
}

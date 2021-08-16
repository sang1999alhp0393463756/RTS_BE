package com.example.demo123.repository;

import com.example.demo123.dto.response.lessonOfStudent;
import com.example.demo123.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    String getLessonByCourseId = "select * from rts.lessons where course_id = ?;";
    @Query(value = getLessonByCourseId, nativeQuery = true)
    List<Lesson> findLessonByCourseId(long idCourse);

    String getLessonByCourseIdForStudy = "SELECT c.id,c.title,c.content,c.image,c.link_video,c.course_id,c.created_date,c.status,a.status as isStudy,c.short_description  FROM rts.user_lesson a  right outer join rts.lessons c on a.lesson_id =c.id where c.course_id =?";
    @Query(value = getLessonByCourseIdForStudy, nativeQuery = true)
    List<lessonOfStudent> getLessonByCourseIdForStudy(long idCourse);
    String getLessonByIdForStudy = "SELECT c.id,c.title,c.content,c.image,c.link_video,c.course_id,c.created_date,c.status,a.status as isStudy,c.short_description  FROM rts.user_lesson a inner join rts.users b on a.user_id = b.id right outer join rts.lessons c on a.lesson_id =c.id where c.id =?";
    @Query(value = getLessonByIdForStudy, nativeQuery = true)
    List<lessonOfStudent> getLessonByIdForStudy(long id);
    String getLessonByLessonId = "select * from rts.lessons where rts.lessons.id = ?;";
    @Query(value = getLessonByLessonId, nativeQuery = true)
    Optional<Lesson> findLessonByLessonId(long id);

    Optional<Lesson> findLessonById(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `rts`.`lessons` WHERE (`course_id` = ?1);\n",nativeQuery = true)
    void deleteCourse_lesson(Long id);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `rts`.`user_lesson` (`status`, `lesson_id`, `user_id`) VALUES ('studied', ?1, ?2)",nativeQuery = true)
    void updateStudy(Long lesson_id,Long user_id);



}

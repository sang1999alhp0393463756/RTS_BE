package com.example.demo123.repository;

import com.example.demo123.dto.response.courseRegistiteried;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    String query = "SELECT *,count(user_id) as student FROM user_course a inner join courses b on a.course_id=b.id group by course_id order by student desc";
    @Query(value = query, nativeQuery = true)
    List<Course> findTop5();

    String query2 = "SELECT * FROM votes a inner join courses b on a.course_id = b.id group by course_id order by rating_toltal desc ";
    @Query(value = query2, nativeQuery = true)
    List<Course> allCourseHaveVote();

    String query3 = "SELECT * from rts.courses where status like 'active'  limit 4 ";
    @Query(value = query3, nativeQuery = true)
    List<Course> top4();

    List<Course> findCourseById(Long id);

    String courseOfUser = "select a.id,a.title,a.thumbnail,a.content,a.sort_description,(select d.full_name from rts.users d where d.id = a.core_expert) as teacherName,b.status,a.price,a.rating_toltal,a.category_id,(select e.name from rts.categories e where e.id = a.category_id) as nameCategory,b.date as registerDate from rts.courses a inner join rts.user_course b on a.id = b.course_id inner join rts.users c on b.user_id = c.id where c.id = ?";
    @Query(value = courseOfUser, nativeQuery = true)
    List<courseRegistiteried> courseOfUser(long id);

    String courseExpertCreate = "select * from courses where courses.core_expert = ? ";
    @Query(value = courseExpertCreate, nativeQuery = true)
    List<Course> courseExpertCreate(Long id);

    @Query(value = "select * from courses where courses.status like ? ", nativeQuery = true)
    List<Course> courseByStatus(String status);

    String getListRegisterAdvisor = "Select * from courses, users, user_course \n" +
            "WHERE courses.id = user_course.course_id and users.id = user_course.user_id;";
    @Query(value = getListRegisterAdvisor, nativeQuery = true)
    List<Course> getListRegisterAdvisor();

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a inner join rts.courses b on a.course_id=b.id where b.id = ?1 and a.status not like \"pending\";",nativeQuery = true)
    List<Course> listStudyActiveCourse(long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT *FROM rts.courses a where a.category_id=?",nativeQuery = true)
    List<Course> countCourseInCagegory(long course_id);
}

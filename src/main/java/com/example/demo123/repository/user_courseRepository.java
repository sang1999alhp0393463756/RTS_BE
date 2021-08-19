package com.example.demo123.repository;

import com.example.demo123.dto.response.StudentsOfCourse;
import com.example.demo123.dto.response.userRegisterCourse;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.user_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface user_courseRepository extends JpaRepository<Role, Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.status like ?1 and a.course_id = ?2",nativeQuery = true)
    List<userRegisterCourse> getByStatus(String status, Long course);
    @Transactional
    @Modifying
    @Query(value = "UPDATE rts.user_course SET status = 'active' WHERE user_id = ?1 and course_id = ?2",nativeQuery = true)
    void updateStauts(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rts.user_course WHERE user_id = ?1 and course_id = ?2",nativeQuery = true)
    void detele(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.user_id = ?1 and a.course_id = ?2 and status not like \"pending\"",nativeQuery = true)
    List<userRegisterCourse> checkRegister(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.user_id = ?1 and a.course_id = ?2",nativeQuery = true)
    List<userRegisterCourse> checkRegister2(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT c.id,(select name from rts.categories where id = c.category_id) as categoryName,title,price,(select full_name from rts.users where id = c.core_expert) as nameTeacher,total_money,withdrawn_money,remaining_amount,full_name,b.date as ngay_dang_ki,phone_number,b.status as status_register,username as email,dob FROM rts.users a inner join rts.user_course b ON  a.id = b.user_id inner join rts.courses c on b.course_id = c.id where c.id =?1",nativeQuery = true)
    List<StudentsOfCourse> getStudentsOfcourse(Long course_id);
    String getListRegisterAdvisor = "Select user_course.date, user_course.status from users, courses, user_course \n" +
            "WHERE courses.id = user_course.course_id and users.id = user_course.user_id;";
    @Query(value = getListRegisterAdvisor, nativeQuery = true)
    List<userRegisterCourse> getListRegisterAdvisor();
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a inner join rts.courses b on a.course_id=b.id where b.id = ?1 and a.status not like \"pending\";",nativeQuery = true)
    List<Course> listStudyActiveCourse(long course_id);
}

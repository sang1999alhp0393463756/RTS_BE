package com.example.demo123.repository;

import com.example.demo123.dto.response.StudentsOfCourse;
import com.example.demo123.dto.response.checkRegister;
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
    @Query(value = "UPDATE rts.user_course SET status = 'active',nguoi_duyet =?1,tien_nop=?2 WHERE user_id = ?3 and course_id = ?4",nativeQuery = true)
    void updateStauts(String nguoi_duyet,float tien_nop,Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rts.user_course WHERE user_id = ?1 and course_id = ?2",nativeQuery = true)
    void detele(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.user_id = ?1 and a.course_id = ?2 and status not like \"pending\"",nativeQuery = true)
    List<checkRegister> checkRegister(Long user_id,Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.user_id = ?1 and a.course_id = ?2",nativeQuery = true)
    List<checkRegister> checkRegister2(Long user_id, Long course_id);
    @Transactional
    @Modifying
    @Query(value = "SELECT c.id,(select name from rts.categories where id = c.category_id) as categoryName,title,price,(select full_name from rts.users where id = c.core_expert) as nameTeacher,total_money,withdrawn_money,remaining_amount,full_name,b.date as ngay_dang_ki,phone_number,b.status as status_register,username as email,dob,c.sale,b.tien_nop FROM rts.users a inner join rts.user_course b ON  a.id = b.user_id inner join rts.courses c on b.course_id = c.id where c.id =?1",nativeQuery = true)
    List<StudentsOfCourse> getStudentsOfcourse(Long course_id);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a inner join rts.courses b on a.course_id=b.id where b.id = ?1 and a.status not like \"pending\";",nativeQuery = true)
    List<Course> listStudyActiveCourse(long course_id);
    @Transactional
    @Modifying
    @Query(value = "select a.full_name as fullName,a.username,a.phone_number,c.title,c.price,b.date,b.status,c.category_id,b.user_id,b.course_id,b.nguoi_duyet,b.tien_nop from rts.users a inner join rts.user_course b on a.id = b.user_id inner join rts.courses c on b.course_id = c.id",nativeQuery = true)
    List<userRegisterCourse> getListRegisterAdvisor();


    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM rts.user_course a  where a.course_id = ?1 and status not like \"pending\"",nativeQuery = true)
    List<checkRegister> listRegister(Long course_id);
}

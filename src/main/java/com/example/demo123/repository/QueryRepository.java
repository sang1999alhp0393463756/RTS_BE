package com.example.demo123.repository;

import com.example.demo123.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface QueryRepository extends JpaRepository<Role, Long>{
    @Transactional
    @Modifying
    @Query(value = "update user_roles set role_id = ?1 where user_id = ?2 ",nativeQuery = true)
    void updateRoleForUser(Integer role_id,Integer user_id);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_course(user_id,course_id,status,tien_nop,date) VALUES (?1, ?2, 'pending',?3)",nativeQuery = true)
    void register(Long user_id, Long course_id,float tien_nop, Date date);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = ?1",nativeQuery = true)
    void deleteRole_user(Long id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_course WHERE user_id = ?1",nativeQuery = true)
    void deleteUser_course(Long id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_course WHERE course_id = ?1",nativeQuery = true)
    void deleteCourse_user(Long id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_exam WHERE exam_id = ?1",nativeQuery = true)
    void deleteExam_user(Long id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM answers WHERE exam_id = ?1",nativeQuery = true)
    void deleteExam_answers(Long id);
}

package com.example.demo123.repository;


import com.example.demo123.dto.response.user_courseRespon;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByTokenEmail(String token);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String PhoneNumber);

    String getListRegisterAdvisor = "Select * from users, courses, user_course \n" +
            "WHERE courses.id = user_course.course_id and users.id = user_course.user_id;";

    @Query(value = getListRegisterAdvisor, nativeQuery = true)
    List<User> getListRegisterAdvisor();


    String getInfoByID = "select * from rts.users where users.id = ?;";

    @Query(value = getInfoByID, nativeQuery = true)
    Optional<User> getInfoByID(Long id);

    String getInfoUserByCourse = "select * from users inner join user_course \n" +
            "on users.id = user_course.user_id\n" +
            "inner join courses\n" +
            "on user_course.course_id = courses.id\n" +
            "where user_course.course_id = ?;";

    @Query(value = getInfoUserByCourse, nativeQuery = true)
    Optional<User> getInfoUserByCourse(Long id);

    String getInfoExpertByCourse = "select * from users inner join courses\n" +
            "on users.username = courses.created_by\n" +
            "where courses.id = ?;";

    @Query(value = getInfoExpertByCourse, nativeQuery = true)
    Optional<User> getInfoExpertByCourse(Long id);

    // Tất cả user đã đăng ký các khóa học
    String getRegisteredList = "select * from users inner join user_course\n" +
            "on users.id = user_course.user_id inner join user_roles\n" +
            "on user_course.user_id = user_roles.user_id inner join roles\n" +
            "on user_roles.role_id = roles.id\n" +
            "where roles.name not like \"ROLE_ADMIN\";";

    @Query(value = getRegisteredList, nativeQuery = true)
    List<User> getRegisteredList();
    String getRegisteredListByStatus = "select b.course_id,c.title,b.user_id,a.full_name ,b.date,b.status from rts.users a inner join rts.user_course b\n" +
            "on a.id  = b.user_id inner join rts.courses c on b.course_id = c.id where b.status like ?";

    @Query(value = getRegisteredListByStatus, nativeQuery = true)
    List<user_courseRespon> getRegisteredListByStatus(String status);

    String getRegisteredListById = "select * from users inner join user_course\n" +
            "on users.id = user_course.user_id inner join user_roles\n" +
            "on user_course.user_id = user_roles.user_id inner join roles\n" +
            "on user_roles.role_id = roles.id\n" +
            "where roles.name not like \"ROLE_ADMIN\" and users.id = ?;";

    @Query(value = getRegisteredListById, nativeQuery = true)
    Optional<User> getRegisteredListById(long id);

    String getUserWithoutAdmin = "select * from users inner join user_roles\n" +
            "on users.id = user_roles.user_id inner join roles\n" +
            "on user_roles.role_id = roles.id\n" +
            "where roles.name not like \"ROLE_ADMIN\" ";
    @Query(value = getUserWithoutAdmin, nativeQuery = true)
    List<User> getAllUserWithoutAdmin();

    // Lấy tất cả user đã đăng ký 1 khóa học
    String userOfCourse = "select * from users inner join user_course\n" +
            "on users.id = user_course.user_id inner join user_roles\n" +
            "on user_course.user_id = user_roles.user_id inner join roles\n" +
            "on user_roles.role_id = roles.id inner join courses\n" +
            "on user_course.course_id = courses.id\n" +
            "where courses.id = ? and roles.name not like \"ROLE_ADMIN\";";

    @Query(value = userOfCourse, nativeQuery = true)
    List<User> userOfCourse(long id);

    @Query(value = "SELECT * FROM rts.users where id != ?", nativeQuery = true)
    List<User> getUserToUpdateMail(Long id);
}
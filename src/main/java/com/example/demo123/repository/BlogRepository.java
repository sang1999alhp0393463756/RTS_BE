package com.example.demo123.repository;

import com.example.demo123.dto.request.blogRequest;
import com.example.demo123.entity.Blog;
import com.example.demo123.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query(value = "SELECT * FROM rts.blogs order by created_date;",nativeQuery = true)
    List<Blog> findAll();
    @Query(value = "SELECT * FROM rts.blogs where  status like 'active' order by created_date;",nativeQuery = true)
    List<Blog> findAllActive();
    @Query(value = "SELECT * FROM rts.blogs where id=? order by created_date;",nativeQuery = true)
    Optional<Blog> findBlogById(Long id);
    @Query(value = "SELECT * FROM rts.blogs where id=? and status like 'active' order by created_date;",nativeQuery = true)
    Optional<Blog> findBlogByIdActive(Long id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `rts`.`blogs` WHERE (`user_id` = ?1) order by created_date;",nativeQuery = true)
    void deleteByUser(Long id_user);
    @Query(value = "SELECT *FROM (SELECT * FROM rts.blogs where status like 'active' order by created_by desc) AS TABLE_A LIMIT 4",nativeQuery = true)
    List<Blog> getTop4();
    @Query(value = "SELECT * FROM rts.blogs where user_id=? order by created_date;",nativeQuery = true)
    List<Blog> findBlogByUserID(Long user_id);

}

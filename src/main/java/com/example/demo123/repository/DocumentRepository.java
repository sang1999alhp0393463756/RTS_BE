package com.example.demo123.repository;

import com.example.demo123.dto.response.documentRespon;
import com.example.demo123.entity.document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository  extends JpaRepository<document, Long> {
    @Query(value = "SELECT * FROM rts.document where course_id = ?;",nativeQuery = true)
    List<document> getAllByCourse(Long courseId);
    @Query(value = "SELECT a.id,a.created_date,a.created_by,a.title,a.security,a.status,a.course_id,b.title as course_name,a.document_name,c.id as category_id,c.name as category_name,a.description FROM rts.document a inner join rts.courses b on a.course_id =b.id inner join rts.categories c on b.category_id = c.id where a.security like 'public' and b.status like 'active'",nativeQuery = true)
    List<documentRespon> getAllBySecurity();
}

package com.example.demo123.repository;

import com.example.demo123.dto.response.PaymentRespon;
import com.example.demo123.entity.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<payment,Long> {
    @Query(value = "SELECT a.id,a.created_date,a.modified_date,a.code,a.course_id,a.money,a.name_bank,a.status,a.stk,a.user_id,b.full_name,c.title as name_course FROM rts.payment a inner join rts.users b on a.user_id = b.id inner join rts.courses c on a.course_id = c.id where a.status like ?;",nativeQuery = true)
    List<PaymentRespon> getAllByStatus(String status);
}

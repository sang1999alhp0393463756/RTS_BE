package com.example.demo123.repository;

import com.example.demo123.entity.User;
import com.example.demo123.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    @Query(value = "SELECT * FROM rts.votes where course_id = ? and user_id =?",nativeQuery = true)
    Vote findVotes(Long course_id, Long user_id);
    @Query(value = "SELECT * FROM rts.votes where course_id = ? ",nativeQuery = true)
    List<Vote> findVotesbyCourse(Long course_id);
}

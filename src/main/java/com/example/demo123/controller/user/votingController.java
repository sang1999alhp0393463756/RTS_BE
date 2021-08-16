package com.example.demo123.controller.user;

import com.amazonaws.services.apigateway.model.Op;
import com.example.demo123.dto.request.votingRequest;
import com.example.demo123.entity.Course;
import com.example.demo123.entity.User;
import com.example.demo123.entity.Vote;
import com.example.demo123.repository.CourseRepository;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user")
public class votingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private VoteRepository voteRepository;
//    @PostMapping("/addVote")
//    public ResponseEntity<?> addVote(@RequestBody votingRequest vote){
//        User u = userRepository.getById(vote.getUser_id());
//        Course c = courseRepository.getById(vote.getCourse_id());
//        if (u!=null && c!=null || !u.getStatus().equals("pending")){
//            Vote vote1 = new Vote();
//            vote1.setUser(u);
//            vote1.setCourse(c);
//            vote1.setRating(vote.getRating());
//            vote1.setStatus("active");
//            voteRepository.save(vote1);
//            return ResponseEntity.ok("success");
//        }else {
//            return ResponseEntity.ok("fail");
//        }
//    }
    @PutMapping("/Vote")
    public ResponseEntity<?> updateVote(@RequestBody votingRequest vote){
        User u = userRepository.getById(vote.getUser_id());
        Course c = courseRepository.getById(vote.getCourse_id());
        if (u!=null && c!=null){
            Vote vote1 = voteRepository.findVotes(vote.getCourse_id(),vote.getUser_id());
            if (vote1!=null){
                vote1.setUser(u);
                vote1.setCourse(c);
                vote1.setRating(vote.getRating());
                vote1.setStatus("active");
                voteRepository.save(vote1);
            }else {
                Vote vote2 = new Vote();
                vote2.setUser(u);
                vote2.setCourse(c);
                vote2.setRating(vote.getRating());
                vote2.setStatus("active");
                voteRepository.save(vote2);
            }
            List<Vote> totalVote = voteRepository.findVotesbyCourse(vote.getCourse_id());
            float total_rating =0;
            for (int i=0;i<totalVote.size();i++){
                total_rating+=totalVote.get(i).getRating();
            }
            total_rating = total_rating/totalVote.size();
            c.setRating_toltal(total_rating);
            courseRepository.save(c);
            return ResponseEntity.ok(total_rating);
        }else {
            return ResponseEntity.ok("fail");
        }
    }
    @GetMapping("/checkVoted")
    public ResponseEntity<?>checkVoted(@RequestParam(name = "userId") Long userId, @RequestParam(name = "courseId") Long courseId){
        Vote vote = voteRepository.findVotes(courseId,userId);
        List<Vote> list = new ArrayList<>();
        list.add(vote);
        return ResponseEntity.ok(list);
    }
}

package com.example.demo123.controller.user;

import com.example.demo123.dto.request.updateEmail;
import com.example.demo123.dto.request.userRequest;
import com.example.demo123.entity.User;
import com.example.demo123.repository.QueryRepository;
import com.example.demo123.repository.RoleRepository;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;


@CrossOrigin
@RestController
@PreAuthorize("hasRole('USER') or hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN') or hasRole('MARKETER')")
@RequestMapping("/user")
public class UserController {
    @Autowired
    QueryRepository queryRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private JavaMailSender sender;
    private AmazonClient amazonClient;
    @Autowired
    UserController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
//    @GetMapping("/showUser")
//    public ResponseEntity<?> showAll() {
//        List<User> list = userRepository.getAllUserWithoutAdmin();
//        List<userRespon> user = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            userRespon infor = new userRespon();
//            infor.setId(list.get(i).getId());
//            infor.setUsername(list.get(i).getUsername());
//            infor.setPhoneNumber(list.get(i).getPhoneNumber());
//            infor.setStatus(list.get(i).getStatus());
//            Set<Role> r = list.get(i).getRoles();
//            List<Role> role = r.stream().collect(Collectors.toList());
//            List<String> roles = new ArrayList<>();
//            for (int j = 0; j < role.size(); j++) {
//                roles.add(role.get(j).getName().toString());
//            }
//            infor.setRoles(roles);
//            infor.setDescription(list.get(i).getDescription());
//            infor.setAvatar(list.get(i).getAvatar());
//            infor.setDob(list.get(i).getDob());
//            user.add(infor);
//        }
//        return ResponseEntity.ok(user);
//    }

    @PutMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody updateEmail updateEmail) {
        User user1 = userRepository.findByUsername(updateEmail.getOldEmail()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + updateEmail.getOldEmail()));
        if (user1 != null && user1.getId() != updateEmail.getId() || user1.getStatus().equals("active")) {
            return ResponseEntity.ok("mày là thằng nào mà dám update info bố");
        } else {
            List<User> list = userRepository.getUserToUpdateMail(updateEmail.getId());
            int check = -1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUsername().equals(updateEmail.getNewEmail())) {
                    check = 1;
                    break;
                }
            }
            if (check == 1) {
                return ResponseEntity.ok("email đã tồn tại");
            } else {
                user1.setUsername(updateEmail.getNewEmail());
                user1.setStatus("pending");
                    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    StringBuilder sb = new StringBuilder();
                    Random random = new Random();
                    int length = 7;
                    for (int i = 0; i < length; i++) {
                        int index = random.nextInt(alphabet.length());
                        char randomChar = alphabet.charAt(index);
                        sb.append(randomChar);
                    }
                    String randomString = sb.toString();
                    user1.setTokenEmail(randomString);

                userRepository.save(user1);
                    //send mail verify
                    MimeMessage message = sender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message);

                    try {
                        helper.setTo(user1.getUsername());
                        String hello= "<h1 style=\"color:blue;\">Xin chào "+user1.getFullName()+"</h1>";
                        String content="<p>bạn vui lòng kích hoạt email để có thể bảo vệ tài khoản của mình và trải nhiệm trọn vẹn dịch vụ của chúng tôi</p>";
                        String button = "<d>"+hello+""+content+"</br><a href=\"https://rts-solution.herokuapp.com/api/auth/verify/?token=" + user1.getTokenEmail() + "\">Active Account</a></d>";
                        helper.setText(button, true);

                        helper.setSubject("Mail From RTS_Learning_Solution");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        return ResponseEntity.ok("Error while sending mail ..");
                    }
                    sender.send(message);

                return ResponseEntity.ok("success");
            }
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUserById(@ModelAttribute userRequest user) {
        User user1 = userRepository.getById(user.getId());
        if (user1 != null && user1.getId() != user.getId()) {
            return ResponseEntity.ok("mày là thằng nào mà dám update info bố");
        } else {
            if (!user.getAvatar().equals("")&&!user.getAvatar().equals("null")){
                user1.setAvatar(user.getAvatar());
            }else {
                user1.setAvatar(user1.getAvatar());
            }
            user1.setFullName(user.getFullName());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setDescription(user.getDescription());
            user1.setDob(user.getDob());
            userRepository.save(user1);
            return ResponseEntity.ok("success");
        }
    }

    @GetMapping("/getUserByCourse/{id}")
    public ResponseEntity<User> getUserByCourse(@PathVariable long id) {
        Optional<User> userOptional = userRepository.getInfoUserByCourse(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


 @PutMapping("/registerExpert")
    public ResponseEntity<?> registerExpert(@RequestParam(name = "id") Long id){
        User user = userRepository.getById(id);
        if(user != null && user.getStatus().toLowerCase().equals("active")){
            Integer userid = Integer.parseInt(id.toString());
            queryRepository.updateRoleForUser(3, userid);
            return ResponseEntity.ok("register expert successfully! ");
        }else {
            return ResponseEntity.ok("account is not active yet or not exist!");
        }
 }

}

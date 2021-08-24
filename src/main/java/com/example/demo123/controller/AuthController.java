package com.example.demo123.controller;


import com.example.demo123.dto.request.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.demo123.service.Ipml.UserDetailsImpl;
import com.example.demo123.entity.ERole;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.dto.request.LoginRequest;
import com.example.demo123.dto.request.SignupRequest;
import com.example.demo123.dto.response.JwtResponse;
import com.example.demo123.repository.RoleRepository;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.security.jwt.JwtUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender sender;
//    @Autowired
//    private Emailservice emailservice;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User user = userRepository.findByUsername(loginRequest.getUsername()) .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + loginRequest.getUsername()));

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                user.getStatus(),
                roles,
                user.getFullName(),
                user.getAvatar()));
    }

    @PostMapping("/lostUser")
    public ResponseEntity<?> emailSend(@RequestHeader String email) {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
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
        if (user != null) {

            String newPass = encoder.encode(randomString);
            user.setPassword(newPass);
        }
        userRepository.save(user);
//        String hello= "<h1 style=\"color:blue;\">Xin chào "+user.getFullName()+"</h1>";
//        String button = hello+"mật khẩu của bạn là : "+randomString+"";
//        String subject = "Mail From RTS_Learning_Solution";
//        EmailRequest emailRequest = new EmailRequest(email,subject,button);
//        Response response=emailservice.sendemail(emailRequest);
//        if(response.getStatusCode()==200||response.getStatusCode()==202)
//            return new ResponseEntity<>("successfully", HttpStatus.OK);
//        return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText("mật khẩu của bạn là : "+randomString+"");
            helper.setSubject("Mail From RTS_Learning_Solution");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Error while sending mail ..");
        }
        sender.send(message);
        return ResponseEntity.ok("Mail Sent Success!");
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.ok("Error: Username is already taken!");
        }

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity.ok("Error: phone number is already in use!");
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword())
                , signUpRequest.getFullName()
                , signUpRequest.getPhoneNumber(), "pending",signUpRequest.getDob());


        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
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
        user.setTokenEmail(randomString);
        user.setRoles(roles);
        userRepository.save(user);
//        String hello= "<h1 style=\"color:blue;\">Xin chào "+signUpRequest.getFullName()+"</h1>";
//        String content="<p>bạn vui lòng kích hoạt email để có thể bảo vệ tài khoản của mình và trải nhiệm trọn vẹn dịch vụ của chúng tôi</p>";
//        String button = "<d>"+hello+""+content+"</br><a href=\"http://localhost:8082/api/auth/verify/?token="+user.getTokenEmail()+"\">Active Account</a></d>";
//        String subject = "Mail From RTS_Learning_Solution";
//        EmailRequest emailRequest = new EmailRequest(user.getUsername(),subject,button);
//        Response response=emailservice.sendemail(emailRequest);
//        if(response.getStatusCode()==200||response.getStatusCode()==202)
//            return new ResponseEntity<>("successfully", HttpStatus.OK);
//        return new ResponseEntity<>("failed to sent",HttpStatus.NOT_FOUND);

        //send mail verify
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(user.getUsername());
            String hello= "<h1 style=\"color:blue;\">Xin chào "+signUpRequest.getFullName()+"</h1>";
            String content="<p>bạn vui lòng kích hoạt email để có thể bảo vệ tài khoản của mình và trải nhiệm trọn vẹn dịch vụ của chúng tôi</p>";
            String button = "<d>"+hello+""+content+"</br><a href=\"https://rts-solution.herokuapp.com/api/auth/verify/?token="+user.getTokenEmail()+"\">Active Account</a></d>";
            helper.setText(button,true);
            helper.setSubject("Mail From RTS_Learning_Solution");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Error while sending mail ..");
        }
        sender.send(message);

        return ResponseEntity.ok("Register successfully!");
    }


    @PostMapping("/changePassword")
    public String changePass(@RequestBody @Valid PasswordDTO dto) {
        String status = null;
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + dto.getUsername()));
        if (!encoder.matches(dto.getOldPassword(), user.getPassword())) {
            status = "Mật khẩu cũ không đúng";
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            status = "Nhắc lại mật khẩu mới không đúng";
        } else {
            user.setPassword(encoder.encode(dto.getNewPassword()));
            status = "change password succefully!";
            userRepository.save(user);
        }
        return status;
    }


    @GetMapping("/verify")
    public String verify(@RequestParam(name = "token") String token) {
        User user = userRepository.findByTokenEmail(token)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with toke: " + token));
        user.setStatus("active");
        userRepository.save(user);
        return "active success!!";
    }


}

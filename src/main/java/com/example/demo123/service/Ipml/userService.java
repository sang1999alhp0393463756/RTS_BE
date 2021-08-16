package com.example.demo123.service.Ipml;

import com.example.demo123.entity.AuthenticationProvider;
import com.example.demo123.entity.ERole;
import com.example.demo123.entity.Role;
import com.example.demo123.entity.User;
import com.example.demo123.repository.RoleRepository;
import com.example.demo123.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class userService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public String processOAuthPostLogin(String username,String fullName) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()==false) {
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
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setAuthProvider(AuthenticationProvider.GOOGLE);
            newUser.setFullName(fullName);
            newUser.setStatus("active");
            newUser.setTokenEmail(randomString);
            newUser.setRoles(roles);
            newUser.setStatus("active");
            userRepository.save(newUser);
            return randomString;
        }else {
            user.get().setStatus("active");
            userRepository.save(user.get());
            return user.get().getTokenEmail();
        }

    }

}

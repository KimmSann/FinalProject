package com.example.demo.service;


import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 유() {
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword); 

        User user = User.builder()
                .name("홍길동")
                .email("hong@example.com")
                .password(encodedPassword)
                .nickname("길동이")
                .profileimg("default.png")
                .role("ROLE_USER")
                .loginType("local")
                
                .build();

        userRepository.save(user);


    }
}
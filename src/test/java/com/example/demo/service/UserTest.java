package com.example.demo.service;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;

@SpringBootTest
public class UserTest {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService service;
	
	void 유저_하나_저장_테스트() {  
        User user = User.builder()
                .name("testuser")
                .email("test@example.com")
                .password("1234")
                .nickname("테스트유저")
                .profileimg("http://example.com/profile.jpg")
                .role("USER")
                .createdate(LocalDateTime.now())
                .build();
 }
}

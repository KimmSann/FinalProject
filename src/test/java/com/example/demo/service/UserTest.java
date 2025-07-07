package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;

@SpringBootTest
public class UserTest {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService service;
	
//	@Test
//	void 유저 추가() {
//		
//	}
}

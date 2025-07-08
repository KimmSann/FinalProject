package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;

@SpringBootTest
public class UserTest {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService service;
	
	@Test
	void 유저추가() {
		
		UserDto user = UserDto.builder()
				.name("둘리")
				.email("hyunjae09122@naver.com")
				.password("1234")
				.nickname("둘리 찐")
				.profileimg("아직없다")
				.role("사용자")
				.build();
		
		boolean access = service.register(user);
		if(access) {
			System.out.println("성공함");
		}
		else {
			System.out.println("실패핰");
		}
		
	}
}

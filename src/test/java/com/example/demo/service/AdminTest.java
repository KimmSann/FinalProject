package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.admin.dto.AdminDto;
import com.example.demo.admin.repository.AdminRepository;
import com.example.demo.admin.service.AdminService;

@SpringBootTest
public class AdminTest {
	
	@Autowired
	AdminRepository repository;
	
	@Autowired
	AdminService service;
	
	@Test
	void 관리자등록() {
		AdminDto dto = AdminDto.builder()
				.managername("관리자형 둘리")
				.build();
		
		boolean access = service.register(dto);
		if(access) {
			System.out.println("성공함");
		}
		else {
			System.out.println("실패핰");
		}
	}
}

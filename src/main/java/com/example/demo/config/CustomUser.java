package com.example.demo.config;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.user.dto.UserDto;

// 프로젝트 내에서 사용하는 user데이터를 
// 시큐리티 내부에서 사용하는 구조로 변환하는 클래스


public class CustomUser extends User {
	
	// UserDto => User (우리꺼x security꺼)
		
	public CustomUser(UserDto dto) {
		super(dto.getEmail(), dto.getPassword(), Arrays.asList(new SimpleGrantedAuthority(dto.getRole())));    
	}

}

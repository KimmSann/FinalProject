package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.dto.SignupDto;


public interface UserService {
	
	
    boolean register(UserDto dto);
    UserDto read(int id);
    UserDto readByUserName(String nickname);
    UserDto login(String email, String password);
    boolean signup(SignupDto dto, MultipartFile file);  
}
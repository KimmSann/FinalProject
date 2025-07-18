package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.SignupDto;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	
    boolean register(UserDto dto);
    UserDto read(int id);
    UserDto login(String email, String password);

    boolean signup(SignupDto dto);  
    
    // 추가
    UserDto readByEmail(String email);
    
    boolean signup(SignupDto dto, MultipartFile file);
    boolean modify(UserDto dto);

}

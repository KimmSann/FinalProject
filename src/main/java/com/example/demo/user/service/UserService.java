package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.dto.LoginDto;

public interface UserService {
    boolean register(UserDto dto);
    UserDto read(int id);
    UserDto readByUserName(String nickname);
    UserDto login(String email, String password);
    boolean signup(SignupDto dto);  
}

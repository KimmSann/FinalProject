package com.example.demo.user.controller;

import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupDto dto) {
        userService.signup(dto);
        return "redirect:/login"; // 회원가입 성공 후 로그인 페이지로
    }
}

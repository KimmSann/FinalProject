package com.example.demo.user.controller;

import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";  // signup.html 뷰 반환
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupDto signupDto) {
        boolean success = userService.signup(signupDto);
        if (success) {
            return "redirect:/signin";  // 회원가입 성공시 로그인 페이지로 이동
        } else {
            return "signup";            // 실패시 다시 회원가입 폼으로
        }
    }

  
}

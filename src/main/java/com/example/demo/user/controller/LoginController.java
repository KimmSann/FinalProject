package com.example.demo.user.controller;

import com.example.demo.user.dto.LoginDto;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 로그인 폼
    @GetMapping("/signin")
    public String showSigninForm() {
        return "signin";
    }

    // 로그인 처리
    @PostMapping("/signin")
    public String login(@ModelAttribute LoginDto dto,
                        HttpSession session,
                        Model model) {

        UserDto user = userService.login(dto.getUsername(), dto.getPassword());

        if (user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "로그인 실패: 이메일 또는 비밀번호 오류");
            return "signin";
        }
    }
}

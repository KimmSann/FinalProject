package com.example.demo.user.controller;

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

    // 로그인 폼 GET 요청
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "message", required = false) String message,
                            Model model) {
        if (message != null) {
            model.addAttribute("message", message); // JavaScript alert용
        }
        return "login"; // templates/login.html
    }

    // 로그인 처리 POST 요청
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        UserDto user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/login?message=로그인 성공!"; // alert로 표시
        } else {
            model.addAttribute("error", "이메일 또는 비밀번호가 틀렸습니다.");
            model.addAttribute("message", "로그인 실패!");
            return "login";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

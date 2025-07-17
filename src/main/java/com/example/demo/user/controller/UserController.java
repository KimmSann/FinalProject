package com.example.demo.user.controller;

import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";  // signup.html 뷰 반환
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupDto signupDto
    		,@RequestParam(name = "files")MultipartFile files) {
    	// 살짝 수정해서 버켓에 프로필 사진 담기 완료
        boolean success = userService.signup(signupDto, files);
        if (success) {
            return "redirect:/signin";  // 회원가입 성공시 로그인 페이지로 이동
        } else {
            return "signup";            // 실패시 다시 회원가입 폼으로
        }
    }

  
}
